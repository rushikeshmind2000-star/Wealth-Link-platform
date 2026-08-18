package com.wealthlink.fund.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ImportFundPriceRequest {
    private UUID fundShareClassId;
    private LocalDate priceDate;
    private String priceType;
    private UUID currencyId;
    private BigDecimal price;
    private UUID providerId;
    private String externalReference;
    private UUID importBatchId;
}
