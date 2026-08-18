package com.wealthlink.reference.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class CountryResponse {
    private UUID id;
    private String isoCode;
    private String name;
    private CurrencyResponse defaultCurrency;
    private String timezone;
}
