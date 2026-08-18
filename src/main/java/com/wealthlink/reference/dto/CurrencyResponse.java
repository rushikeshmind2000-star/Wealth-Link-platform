package com.wealthlink.reference.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class CurrencyResponse {
    private UUID id;
    private String isoCode;
    private String name;
    private Integer minorUnitDigits;
    private String status;
}
