package com.wealthlink.ledger.service;

import com.wealthlink.ledger.dto.CreateJournalEntryRequest;
import com.wealthlink.ledger.dto.CreateJournalRequest;
import com.wealthlink.ledger.dto.JournalResponse;
import com.wealthlink.ledger.entity.*;
import com.wealthlink.ledger.repository.JournalRepository;
import com.wealthlink.ledger.repository.LedgerAccountRepository;
import com.wealthlink.reference.entity.Currency;
import com.wealthlink.reference.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JournalService {

    private final JournalRepository journalRepository;
    private final LedgerAccountRepository ledgerAccountRepository;
    private final CurrencyRepository currencyRepository;

    public List<JournalResponse> getAll() {
        return journalRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public JournalResponse getById(UUID id) {
        Journal journal = journalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Journal not found"));
        return mapToResponse(journal);
    }

    @Transactional
    public JournalResponse createJournal(CreateJournalRequest request) {
        if (request.getIdempotencyKey() != null) {
            Optional<Journal> existing = journalRepository.findByIdempotencyKey(request.getIdempotencyKey());
            if (existing.isPresent()) {
                return mapToResponse(existing.get());
            }
        }

        validateDoubleEntry(request.getEntries());

        Journal journal = new Journal();
        journal.setJournalReference(UUID.randomUUID().toString());
        journal.setDescription(request.getDescription());
        journal.setStatus(JournalStatus.POSTED); // Post immediately for now
        journal.setJournalType(JournalType.valueOf(request.getJournalType()));
        journal.setJournalDate(request.getJournalDate() != null ? request.getJournalDate() : LocalDate.now());
        journal.setReferenceType(request.getReferenceType());
        journal.setReferenceId(request.getReferenceId());
        journal.setIdempotencyKey(request.getIdempotencyKey());
        
        List<JournalEntry> entries = new ArrayList<>();
        for (CreateJournalEntryRequest entryReq : request.getEntries()) {
            JournalEntry entry = new JournalEntry();
            entry.setJournal(journal);
            entry.setLedgerAccount(ledgerAccountRepository.findById(entryReq.getLedgerAccountId())
                    .orElseThrow(() -> new RuntimeException("Ledger Account not found")));
            entry.setDirection(JournalEntryDirection.valueOf(entryReq.getDirection()));
            entry.setAmount(entryReq.getAmount());
            entry.setCurrency(currencyRepository.findById(entryReq.getCurrencyId())
                    .orElseThrow(() -> new RuntimeException("Currency not found")));
            entry.setDescription(entryReq.getDescription());
            entries.add(entry);
        }
        
        journal.setEntries(entries);
        Journal savedJournal = journalRepository.save(journal);
        return mapToResponse(savedJournal);
    }

    private void validateDoubleEntry(List<CreateJournalEntryRequest> entries) {
        if (entries == null || entries.isEmpty()) {
            throw new IllegalArgumentException("Journal must have at least two entries");
        }
        
        Map<UUID, BigDecimal> currencyBalances = new HashMap<>();
        
        for (CreateJournalEntryRequest entry : entries) {
            if (entry.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Entry amount must be strictly positive");
            }
            
            BigDecimal currentBalance = currencyBalances.getOrDefault(entry.getCurrencyId(), BigDecimal.ZERO);
            if (JournalEntryDirection.DEBIT.name().equals(entry.getDirection())) {
                currencyBalances.put(entry.getCurrencyId(), currentBalance.add(entry.getAmount()));
            } else if (JournalEntryDirection.CREDIT.name().equals(entry.getDirection())) {
                currencyBalances.put(entry.getCurrencyId(), currentBalance.subtract(entry.getAmount()));
            } else {
                throw new IllegalArgumentException("Invalid direction: " + entry.getDirection());
            }
        }
        
        for (Map.Entry<UUID, BigDecimal> balanceEntry : currencyBalances.entrySet()) {
            if (balanceEntry.getValue().compareTo(BigDecimal.ZERO) != 0) {
                throw new IllegalArgumentException("Journal is unbalanced for currency ID: " + balanceEntry.getKey());
            }
        }
    }

    private JournalResponse mapToResponse(Journal journal) {
        return JournalResponse.builder()
                .id(journal.getId())
                .journalType(journal.getJournalType().name())
                .postingDate(journal.getPostingDate())
                .valueDate(journal.getValueDate())
                .status(journal.getStatus().name())
                .entries(journal.getEntries().stream().map(e -> 
                    JournalResponse.JournalEntryResponse.builder()
                        .ledgerAccountId(e.getLedgerAccount().getId())
                        .direction(e.getDirection().name())
                        .amount(e.getAmount())
                        .currency(e.getCurrency().getIsoCode())
                        .build()
                ).collect(Collectors.toList()))
                .build();
    }
}
