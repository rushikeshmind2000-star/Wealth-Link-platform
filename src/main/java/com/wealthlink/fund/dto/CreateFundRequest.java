package com.wealthlink.fund.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateFundRequest {
    private String isin;
    private String name;
    private String fundType;
    private UUID baseCurrencyId;
    private UUID domicileCountryId;
}
