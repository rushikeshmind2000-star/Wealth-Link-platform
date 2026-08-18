package com.wealthlink.dividend.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class DividendAllocationResponse {
    private UUID id;
    private UUID portfolioId;
    private BigDecimal positionQuantity;
    private BigDecimal grossAmount;
    private BigDecimal taxAmount;
    private BigDecimal netAmount;
    private String currency;
    private String status;
    private UUID journalId;
}
