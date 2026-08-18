package com.wealthlink.trade.service;

import com.wealthlink.account.entity.Account;
import com.wealthlink.account.entity.AccountStatus;
import com.wealthlink.account.repository.AccountRepository;
import com.wealthlink.fund.entity.Fund;
import com.wealthlink.fund.entity.FundShareClass;
import com.wealthlink.fund.repository.FundRepository;
import com.wealthlink.fund.repository.FundShareClassRepository;
import com.wealthlink.ledger.dto.CreateLedgerAccountRequest;
import com.wealthlink.ledger.entity.LedgerAccountType;
import com.wealthlink.ledger.service.LedgerAccountService;
import com.wealthlink.portfolio.entity.Portfolio;
import com.wealthlink.portfolio.entity.PortfolioStatus;
import com.wealthlink.portfolio.repository.PortfolioRepository;
import com.wealthlink.portfolio.service.PositionService;
import com.wealthlink.reference.entity.Currency;
import com.wealthlink.reference.repository.CurrencyRepository;
import com.wealthlink.trade.dto.CreateOrderRequest;
import com.wealthlink.trade.dto.CreateSettlementRequest;
import com.wealthlink.trade.dto.ExecutionResponse;
import com.wealthlink.trade.dto.RecordExecutionRequest;
import com.wealthlink.trade.entity.TradeOrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TradeLifecycleIntegrationTest {

    @Autowired private TradeOrderService tradeOrderService;
    @Autowired private TradeExecutionService tradeExecutionService;
    @Autowired private SettlementService settlementService;
    @Autowired private LedgerAccountService ledgerAccountService;
    @Autowired private PositionService positionService;

    @Autowired private AccountRepository accountRepository;
    @Autowired private PortfolioRepository portfolioRepository;
    @Autowired private CurrencyRepository currencyRepository;
    @Autowired private com.wealthlink.reference.repository.CountryRepository countryRepository;
    @Autowired private FundRepository fundRepository;
    @Autowired private FundShareClassRepository fundShareClassRepository;

    private Portfolio portfolio;
    private FundShareClass fundShareClass;
    private Currency currency;
    private Account account;

    @BeforeEach
    void setup() {
        currency = new Currency();
        currency.setIsoCode(UUID.randomUUID().toString().substring(0, 3).toUpperCase()); // 3 chars max
        currency.setName("Test Dollar");
        currency.setMinorUnitDigits((short) 2);
        currency = currencyRepository.save(currency);

        com.wealthlink.reference.entity.Country country = new com.wealthlink.reference.entity.Country();
        country.setIsoCode("US");
        country.setName("United States");
        country.setDefaultCurrency(currency);
        country.setTimezone("UTC");
        country = countryRepository.save(country);

        account = new Account();
        account.setAccountNumber("ACC-" + UUID.randomUUID().toString().substring(0, 8));
        account.setAccountType(com.wealthlink.account.entity.AccountType.INVESTMENT);
        account.setCurrency(currency);
        account.setCountry(country);
        account.setStatus(AccountStatus.ACTIVE);
        account = accountRepository.save(account);

        portfolio = new Portfolio();
        portfolio.setAccount(account);
        portfolio.setPortfolioNumber("P-" + UUID.randomUUID().toString().substring(0, 8));
        portfolio.setPortfolioType(com.wealthlink.portfolio.entity.PortfolioType.STANDARD);
        portfolio.setBaseCurrency(currency);
        portfolio.setStatus(PortfolioStatus.ACTIVE);
        portfolio = portfolioRepository.save(portfolio);

        Fund fund = new Fund();
        fund.setName("Tech Growth Fund");
        fund.setIsin("US1234567890");
        fund.setDomicileCountry(country);
        fund.setBaseCurrency(currency);
        fund = fundRepository.save(fund);

        fundShareClass = new FundShareClass();
        fundShareClass.setFund(fund);
        fundShareClass.setClassCode("CLASS-A");
        fundShareClass.setName("Class A");
        fundShareClass.setCurrency(currency);
        fundShareClass = fundShareClassRepository.save(fundShareClass);

        // Setup Ledger Accounts
        CreateLedgerAccountRequest cashReq = new CreateLedgerAccountRequest();
        cashReq.setAccountCode("CASH-" + account.getId().toString().substring(0, 8));
        cashReq.setAccountName("Cash Account");
        cashReq.setLedgerAccountType(LedgerAccountType.CASH.name());
        cashReq.setCurrencyId(currency.getId());
        cashReq.setAccountId(account.getId());
        ledgerAccountService.createLedgerAccount(cashReq);

        CreateLedgerAccountRequest posReq = new CreateLedgerAccountRequest();
        posReq.setAccountCode("POS-" + portfolio.getId().toString().substring(0, 8));
        posReq.setAccountName("Position Account");
        posReq.setLedgerAccountType(LedgerAccountType.POSITION.name());
        posReq.setCurrencyId(currency.getId());
        posReq.setPortfolioId(portfolio.getId());
        ledgerAccountService.createLedgerAccount(posReq);
    }

    @Test
    void testEndToEndTradeLifecycle() {
        // 1. Create Trade Order (BUY)
        CreateOrderRequest orderReq = new CreateOrderRequest();
        orderReq.setPortfolioId(portfolio.getId());
        orderReq.setFundShareClassId(fundShareClass.getId());
        orderReq.setOrderType("BUY");
        orderReq.setQuantity(new BigDecimal("10.00"));
        orderReq.setLimitPrice(new BigDecimal("150.00"));
        orderReq.setCurrencyId(currency.getId());
        orderReq.setIdempotencyKey("idem-" + UUID.randomUUID());

        var orderRes = tradeOrderService.createOrder(orderReq);
        assertThat(orderRes.getStatus()).isEqualTo(TradeOrderStatus.PENDING.name());

        // 2. Record Execution
        RecordExecutionRequest execReq = new RecordExecutionRequest();
        execReq.setOrderId(orderRes.getId());
        execReq.setTradeDate(LocalDate.now());
        execReq.setExecutedQuantity(new BigDecimal("10.00"));
        execReq.setExecutedPrice(new BigDecimal("150.00"));
        execReq.setGrossAmount(new BigDecimal("1500.00"));
        execReq.setNetAmount(new BigDecimal("1500.00"));
        execReq.setCurrencyId(currency.getId());

        ExecutionResponse execRes = tradeExecutionService.recordExecution(orderRes.getId(), execReq);
        assertThat(execRes.getExecutedQuantity()).isEqualByComparingTo(new BigDecimal("10.00"));

        // 3. Settle Execution -> triggers Journal creation and Position update
        CreateSettlementRequest setReq = new CreateSettlementRequest();
        setReq.setTradeExecutionId(execRes.getId());
        setReq.setSettlementDate(LocalDate.now().plusDays(2));
        setReq.setSettlementAmount(new BigDecimal("1500.00"));
        setReq.setCurrencyId(currency.getId());

        var setRes = settlementService.createSettlement(execRes.getId(), setReq);
        assertThat(setRes.getSettlementStatus()).isEqualTo("SETTLED");

        // 4. Verify Ledger Balances (Cash should be credited/decreased, Position should be debited/increased)
        // Note: For simplicity, LedgerAccountService getBalance uses SUM of debits - credits
        var posAccounts = ledgerAccountService.getAll().stream().filter(a -> a.getLedgerAccountType().equals(LedgerAccountType.POSITION.name())).findFirst().get();
        var posBal = ledgerAccountService.getBalance(posAccounts.getId());
        assertThat(posBal.getBalance()).isEqualByComparingTo(new BigDecimal("1500.00")); 
        
        var cashAccounts = ledgerAccountService.getAll().stream().filter(a -> a.getLedgerAccountType().equals(LedgerAccountType.CASH.name())).findFirst().get();
        var cashBal = ledgerAccountService.getBalance(cashAccounts.getId());
        assertThat(cashBal.getBalance()).isEqualByComparingTo(new BigDecimal("-1500.00")); 

        // 5. Verify Position Rebuild
        var positions = positionService.getAll();
        assertThat(positions).hasSize(1);
        var pos = positions.get(0);
        assertThat(pos.getQuantity()).isEqualByComparingTo(new BigDecimal("10.00"));

        var rebuiltPos = positionService.rebuildPosition(pos.getId());
        assertThat(rebuiltPos.getMarketValue()).isEqualByComparingTo(new BigDecimal("1500.00"));
    }
}
