package com.wealthlink.dividend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateDividendRequest {
    private UUID fundShareClassId;
    private LocalDate exDate;
    private LocalDate recordDate;
    private LocalDate paymentDate;
    private BigDecimal dividendPerUnit;
    private UUID currencyId;
    private String source;
}
