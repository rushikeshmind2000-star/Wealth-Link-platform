package com.wealthlink.dividend.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class CalculateDividendResponse {
    private UUID dividendEventId;
    private Integer eligiblePortfolios;
    private BigDecimal totalUnits;
    private BigDecimal grossDividend;
    private String status;
}
