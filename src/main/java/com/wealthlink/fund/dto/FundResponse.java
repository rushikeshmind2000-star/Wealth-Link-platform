package com.wealthlink.fund.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class FundResponse {
    private UUID id;
    private String isin;
    private String name;
    private String fundType;
    private String baseCurrency;
    private String domicileCountry;
    private String status;
}
