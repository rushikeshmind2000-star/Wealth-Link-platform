package com.wealthlink.ledger.service;

import com.wealthlink.account.repository.AccountRepository;
import com.wealthlink.ledger.dto.CreateLedgerAccountRequest;
import com.wealthlink.ledger.dto.LedgerAccountResponse;
import com.wealthlink.ledger.dto.LedgerBalanceResponse;
import com.wealthlink.ledger.entity.JournalEntry;
import com.wealthlink.ledger.entity.JournalEntryDirection;
import com.wealthlink.ledger.entity.LedgerAccount;
import com.wealthlink.ledger.entity.LedgerAccountType;
import com.wealthlink.ledger.repository.JournalEntryRepository;
import com.wealthlink.ledger.repository.LedgerAccountRepository;
import com.wealthlink.portfolio.repository.PortfolioRepository;
import com.wealthlink.reference.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LedgerAccountService {

    private final LedgerAccountRepository ledgerAccountRepository;
    private final CurrencyRepository currencyRepository;
    private final AccountRepository accountRepository;
    private final PortfolioRepository portfolioRepository;
    private final JournalEntryRepository journalEntryRepository;

    public List<LedgerAccountResponse> getAll() {
        return ledgerAccountRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public LedgerAccountResponse getById(UUID id) {
        LedgerAccount account = ledgerAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LedgerAccount not found"));
        return mapToResponse(account);
    }

    @Transactional
    public LedgerAccountResponse createLedgerAccount(CreateLedgerAccountRequest request) {
        LedgerAccount account = new LedgerAccount();
        account.setAccountCode(request.getAccountCode());
        account.setAccountName(request.getAccountName());
        account.setLedgerAccountType(LedgerAccountType.valueOf(request.getLedgerAccountType()));
        account.setCurrency(currencyRepository.findById(request.getCurrencyId())
                .orElseThrow(() -> new RuntimeException("Currency not found")));
        
        if (request.getAccountId() != null) {
            account.setAccount(accountRepository.findById(request.getAccountId())
                    .orElseThrow(() -> new RuntimeException("Account not found")));
        }
        
        if (request.getPortfolioId() != null) {
            account.setPortfolio(portfolioRepository.findById(request.getPortfolioId())
                    .orElseThrow(() -> new RuntimeException("Portfolio not found")));
        }
        
        account.setDescription(request.getDescription());
        
        LedgerAccount savedAccount = ledgerAccountRepository.save(account);
        return mapToResponse(savedAccount);
    }

    public LedgerBalanceResponse getBalance(UUID ledgerAccountId) {
        LedgerAccount account = ledgerAccountRepository.findById(ledgerAccountId)
                .orElseThrow(() -> new RuntimeException("LedgerAccount not found"));
                
        List<JournalEntry> entries = journalEntryRepository.findByLedgerAccountId(ledgerAccountId);
        
        BigDecimal balance = BigDecimal.ZERO;
        
        boolean isDebitNormalBalance = isDebitNormalBalance(account.getLedgerAccountType());
        
        for (JournalEntry entry : entries) {
            if (entry.getDirection() == JournalEntryDirection.DEBIT) {
                balance = isDebitNormalBalance ? balance.add(entry.getAmount()) : balance.subtract(entry.getAmount());
            } else {
                balance = isDebitNormalBalance ? balance.subtract(entry.getAmount()) : balance.add(entry.getAmount());
            }
        }
        
        return LedgerBalanceResponse.builder()
                .ledgerAccountId(account.getId())
                .currency(account.getCurrency().getIsoCode())
                .balance(balance)
                .asOf(java.time.LocalDate.now())
                .build();
    }
    
    private boolean isDebitNormalBalance(LedgerAccountType type) {
        // ASSETS and EXPENSES have a normal DEBIT balance.
        // CASH, POSITION, FEE, TAX are all generally treated as normal DEBIT balance in this simplified model.
        // If there were LIABILITIES or EQUITY or REVENUE, they would return false.
        return true; 
    }

    private LedgerAccountResponse mapToResponse(LedgerAccount account) {
        return LedgerAccountResponse.builder()
                .id(account.getId())
                .accountCode(account.getAccountCode())
                .accountName(account.getAccountName())
                .ledgerAccountType(account.getLedgerAccountType().name())
                .status(account.getStatus().name())
                .currency(account.getCurrency().getIsoCode())
                .balance(account.getBalance())
                .build();
    }
}
