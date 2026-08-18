package com.wealthlink.ledger.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class LedgerAccountTest {

    @Test
    void prePersistSetsDefaults() {
        LedgerAccount account = new LedgerAccount();
        assertThat(account.getCreatedAt()).isNull();
        assertThat(account.getUpdatedAt()).isNull();
        assertThat(account.getStatus()).isNull();

        account.onCreate();

        assertThat(account.getCreatedAt()).isNotNull();
        assertThat(account.getUpdatedAt()).isNotNull();
        assertThat(account.getStatus()).isEqualTo(LedgerAccountStatus.ACTIVE);
        assertThat(account.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void prePersistKeepsExistingValues() {
        Instant past = Instant.parse("2020-01-01T00:00:00Z");
        BigDecimal balance = new BigDecimal("50000.00");
        LedgerAccount account = LedgerAccount.builder()
                .createdAt(past)
                .updatedAt(past)
                .status(LedgerAccountStatus.CLOSED)
                .balance(balance)
                .build();

        account.onCreate();

        assertThat(account.getCreatedAt()).isEqualTo(past);
        assertThat(account.getUpdatedAt()).isEqualTo(past);
        assertThat(account.getStatus()).isEqualTo(LedgerAccountStatus.CLOSED);
        assertThat(account.getBalance()).isEqualByComparingTo(balance);
    }
}
