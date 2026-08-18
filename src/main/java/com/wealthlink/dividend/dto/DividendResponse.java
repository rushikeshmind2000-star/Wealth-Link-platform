package com.wealthlink.dividend.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class DividendResponse {
    private UUID id;
    private UUID fundShareClassId;
    private LocalDate exDate;
    private LocalDate recordDate;
    private LocalDate paymentDate;
    private BigDecimal dividendPerUnit;
    private String currency;
    private String status;
}
