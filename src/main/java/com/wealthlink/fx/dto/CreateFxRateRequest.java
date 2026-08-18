package com.wealthlink.fx.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateFxRateRequest {
    private UUID baseCurrencyId;
    private UUID quoteCurrencyId;
    private LocalDate rateDate;
    private String rateType;
    private BigDecimal rate;
    private UUID sourceId;
    private Instant effectiveAt;
}
