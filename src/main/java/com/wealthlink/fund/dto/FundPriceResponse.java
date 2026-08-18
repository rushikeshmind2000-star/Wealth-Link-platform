package com.wealthlink.fund.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FundPriceResponse {
    private UUID id;
    private UUID fundShareClassId;
    private LocalDate priceDate;
    private String priceType;
    private BigDecimal price;
    private String currency;
    private String validationStatus;
    private String provider;
}
