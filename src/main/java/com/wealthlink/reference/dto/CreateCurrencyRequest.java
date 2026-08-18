package com.wealthlink.reference.dto;

import lombok.Data;

@Data
public class CreateCurrencyRequest {
    private String isoCode;
    private String name;
    private Integer minorUnitDigits;
}
