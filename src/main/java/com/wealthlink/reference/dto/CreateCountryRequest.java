package com.wealthlink.reference.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateCountryRequest {
    private String isoCode;
    private String name;
    private UUID defaultCurrencyId;
    private String timezone;
}
