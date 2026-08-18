package com.wealthlink.trade.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class TradeExecutionTest {

    @Test
    void prePersistSetsDefaults() {
        TradeExecution exec = new TradeExecution();
        assertThat(exec.getCreatedAt()).isNull();
        assertThat(exec.getUpdatedAt()).isNull();
        assertThat(exec.getStatus()).isNull();

        exec.onCreate();

        assertThat(exec.getCreatedAt()).isNotNull();
        assertThat(exec.getUpdatedAt()).isNotNull();
        assertThat(exec.getStatus()).isEqualTo(TradeExecutionStatus.PENDING);
        assertThat(exec.getFee()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(exec.getTax()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void prePersistKeepsExistingValues() {
        Instant past = Instant.parse("2020-01-01T00:00:00Z");
        BigDecimal fee = new BigDecimal("12.50");
        TradeExecution exec = TradeExecution.builder()
                .createdAt(past)
                .updatedAt(past)
                .status(TradeExecutionStatus.CONFIRMED)
                .fee(fee)
                .build();

        exec.onCreate();

        assertThat(exec.getCreatedAt()).isEqualTo(past);
        assertThat(exec.getUpdatedAt()).isEqualTo(past);
        assertThat(exec.getStatus()).isEqualTo(TradeExecutionStatus.CONFIRMED);
        assertThat(exec.getFee()).isEqualByComparingTo(fee);
    }
}
