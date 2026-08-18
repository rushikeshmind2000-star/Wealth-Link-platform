package com.wealthlink.fx.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class FxRateResponse {
    private UUID id;
    private String baseCurrency;
    private String quoteCurrency;
    private LocalDate rateDate;
    private String rateType;
    private BigDecimal rate;
    private String source;
    private Instant createdAt;
}
