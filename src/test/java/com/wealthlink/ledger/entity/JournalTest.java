package com.wealthlink.ledger.entity;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class JournalTest {

    @Test
    void prePersistSetsDefaults() {
        Journal journal = new Journal();
        assertThat(journal.getCreatedAt()).isNull();
        assertThat(journal.getUpdatedAt()).isNull();
        assertThat(journal.getStatus()).isNull();

        journal.onCreate();

        assertThat(journal.getCreatedAt()).isNotNull();
        assertThat(journal.getUpdatedAt()).isNotNull();
        assertThat(journal.getStatus()).isEqualTo(JournalStatus.DRAFT);
    }

    @Test
    void prePersistKeepsExistingValues() {
        Instant past = Instant.parse("2020-01-01T00:00:00Z");
        Journal journal = Journal.builder()
                .createdAt(past)
                .updatedAt(past)
                .status(JournalStatus.POSTED)
                .journalDate(LocalDate.of(2020, 1, 1))
                .build();

        journal.onCreate();

        assertThat(journal.getCreatedAt()).isEqualTo(past);
        assertThat(journal.getUpdatedAt()).isEqualTo(past);
        assertThat(journal.getStatus()).isEqualTo(JournalStatus.POSTED);
    }
}
