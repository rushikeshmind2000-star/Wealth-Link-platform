package com.wealthlink.trade.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class TradeOrderTest {

    @Test
    void prePersistSetsDefaults() {
        TradeOrder order = new TradeOrder();
        assertThat(order.getCreatedAt()).isNull();
        assertThat(order.getUpdatedAt()).isNull();
        assertThat(order.getStatus()).isNull();

        order.onCreate();

        assertThat(order.getCreatedAt()).isNotNull();
        assertThat(order.getUpdatedAt()).isNotNull();
        assertThat(order.getStatus()).isEqualTo(TradeOrderStatus.NEW);
    }

    @Test
    void prePersistKeepsExistingValues() {
        Instant past = Instant.parse("2020-01-01T00:00:00Z");
        TradeOrder order = TradeOrder.builder()
                .createdAt(past)
                .updatedAt(past)
                .status(TradeOrderStatus.FILLED)
                .requestedQuantity(BigDecimal.TEN)
                .build();

        order.onCreate();

        assertThat(order.getCreatedAt()).isEqualTo(past);
        assertThat(order.getUpdatedAt()).isEqualTo(past);
        assertThat(order.getStatus()).isEqualTo(TradeOrderStatus.FILLED);
    }
}
