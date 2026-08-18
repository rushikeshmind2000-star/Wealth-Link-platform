package com.wealthlink.ledger.service;

import com.wealthlink.ledger.dto.CreateJournalEntryRequest;
import com.wealthlink.ledger.dto.CreateJournalRequest;
import com.wealthlink.ledger.dto.CreateLedgerAccountRequest;
import com.wealthlink.ledger.dto.LedgerAccountResponse;
import com.wealthlink.ledger.dto.LedgerBalanceResponse;
import com.wealthlink.ledger.entity.LedgerAccountType;
import com.wealthlink.reference.entity.Currency;
import com.wealthlink.reference.repository.CurrencyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class LedgerAccountingEngineTest {

    @Autowired
    private JournalService journalService;

    @Autowired
    private LedgerAccountService ledgerAccountService;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    void testDoubleEntryValidation_Unbalanced() {
        Currency currency = new Currency();
        currency.setIsoCode("USD");
        currency.setName("US Dollar");
        currency.setMinorUnitDigits((short) 2);
        currency = currencyRepository.save(currency);

        CreateLedgerAccountRequest acc1Req = new CreateLedgerAccountRequest();
        acc1Req.setAccountCode("CASH-" + UUID.randomUUID());
        acc1Req.setAccountName("Cash");
        acc1Req.setLedgerAccountType(LedgerAccountType.CASH.name());
        acc1Req.setCurrencyId(currency.getId());
        LedgerAccountResponse acc1 = ledgerAccountService.createLedgerAccount(acc1Req);

        CreateLedgerAccountRequest acc2Req = new CreateLedgerAccountRequest();
        acc2Req.setAccountCode("FEE-" + UUID.randomUUID());
        acc2Req.setAccountName("Fee Income");
        acc2Req.setLedgerAccountType(LedgerAccountType.FEE.name());
        acc2Req.setCurrencyId(currency.getId());
        LedgerAccountResponse acc2 = ledgerAccountService.createLedgerAccount(acc2Req);

        CreateJournalEntryRequest debit = new CreateJournalEntryRequest();
        debit.setLedgerAccountId(acc1.getId());
        debit.setDirection("DEBIT");
        debit.setAmount(new BigDecimal("100.00"));
        debit.setCurrencyId(currency.getId());

        CreateJournalEntryRequest credit = new CreateJournalEntryRequest();
        credit.setLedgerAccountId(acc2.getId());
        credit.setDirection("CREDIT");
        credit.setAmount(new BigDecimal("90.00")); // Unbalanced!
        credit.setCurrencyId(currency.getId());

        CreateJournalRequest journalReq = new CreateJournalRequest();
        journalReq.setDescription("Unbalanced Test");
        journalReq.setJournalType("TRADE");
        journalReq.setEntries(Arrays.asList(debit, credit));

        assertThatThrownBy(() -> journalService.createJournal(journalReq))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("unbalanced");
    }

    @Test
    void testDoubleEntryValidation_Balanced_And_Ledger_Balance() {
        Currency currency = new Currency();
        currency.setIsoCode("EUR");
        currency.setName("Euro");
        currency.setMinorUnitDigits((short) 2);
        currency = currencyRepository.save(currency);

        CreateLedgerAccountRequest acc1Req = new CreateLedgerAccountRequest();
        acc1Req.setAccountCode("CASH-" + UUID.randomUUID());
        acc1Req.setAccountName("Cash");
        acc1Req.setLedgerAccountType(LedgerAccountType.CASH.name());
        acc1Req.setCurrencyId(currency.getId());
        LedgerAccountResponse acc1 = ledgerAccountService.createLedgerAccount(acc1Req);

        CreateLedgerAccountRequest acc2Req = new CreateLedgerAccountRequest();
        acc2Req.setAccountCode("POS-" + UUID.randomUUID());
        acc2Req.setAccountName("Positions");
        acc2Req.setLedgerAccountType(LedgerAccountType.POSITION.name());
        acc2Req.setCurrencyId(currency.getId());
        LedgerAccountResponse acc2 = ledgerAccountService.createLedgerAccount(acc2Req);

        CreateJournalEntryRequest debit = new CreateJournalEntryRequest();
        debit.setLedgerAccountId(acc1.getId());
        debit.setDirection("DEBIT");
        debit.setAmount(new BigDecimal("100.00"));
        debit.setCurrencyId(currency.getId());

        CreateJournalEntryRequest credit = new CreateJournalEntryRequest();
        credit.setLedgerAccountId(acc2.getId());
        credit.setDirection("CREDIT");
        credit.setAmount(new BigDecimal("100.00")); 
        credit.setCurrencyId(currency.getId());

        CreateJournalRequest journalReq = new CreateJournalRequest();
        journalReq.setDescription("Balanced Test");
        journalReq.setJournalType("TRADE");
        journalReq.setEntries(Arrays.asList(debit, credit));

        // This should succeed
        journalService.createJournal(journalReq);

        // Verify balances
        LedgerBalanceResponse bal1 = ledgerAccountService.getBalance(acc1.getId());
        assertThat(bal1.getBalance()).isEqualByComparingTo(new BigDecimal("100.00"));

        LedgerBalanceResponse bal2 = ledgerAccountService.getBalance(acc2.getId());
        assertThat(bal2.getBalance()).isEqualByComparingTo(new BigDecimal("-100.00"));
    }
}
