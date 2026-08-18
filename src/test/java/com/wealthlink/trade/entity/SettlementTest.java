package com.wealthlink.trade.entity;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class SettlementTest {

    @Test
    void prePersistSetsDefaults() {
        Settlement settlement = new Settlement();
        assertThat(settlement.getCreatedAt()).isNull();
        assertThat(settlement.getUpdatedAt()).isNull();
        assertThat(settlement.getStatus()).isNull();

        settlement.onCreate();

        assertThat(settlement.getCreatedAt()).isNotNull();
        assertThat(settlement.getUpdatedAt()).isNotNull();
        assertThat(settlement.getStatus()).isEqualTo(SettlementStatus.PENDING);
    }

    @Test
    void prePersistKeepsExistingValues() {
        Instant past = Instant.parse("2020-01-01T00:00:00Z");
        Settlement settlement = Settlement.builder()
                .createdAt(past)
                .updatedAt(past)
                .status(SettlementStatus.SETTLED)
                .settledAt(past)
                .build();

        settlement.onCreate();

        assertThat(settlement.getCreatedAt()).isEqualTo(past);
        assertThat(settlement.getUpdatedAt()).isEqualTo(past);
        assertThat(settlement.getStatus()).isEqualTo(SettlementStatus.SETTLED);
        assertThat(settlement.getSettledAt()).isEqualTo(past);
    }
}
