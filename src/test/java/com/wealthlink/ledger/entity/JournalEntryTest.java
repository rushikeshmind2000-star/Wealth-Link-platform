package com.wealthlink.ledger.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class JournalEntryTest {

    @Test
    void prePersistSetsCreatedAt() {
        JournalEntry entry = new JournalEntry();
        assertThat(entry.getCreatedAt()).isNull();

        entry.onCreate();

        assertThat(entry.getCreatedAt()).isNotNull();
    }

    @Test
    void prePersistKeepsExistingCreatedAt() {
        Instant past = Instant.parse("2020-01-01T00:00:00Z");
        JournalEntry entry = JournalEntry.builder()
                .createdAt(past)
                .direction(JournalEntryDirection.DEBIT)
                .amount(new BigDecimal("1000.00"))
                .build();

        entry.onCreate();

        assertThat(entry.getCreatedAt()).isEqualTo(past);
        assertThat(entry.getDirection()).isEqualTo(JournalEntryDirection.DEBIT);
    }
}
