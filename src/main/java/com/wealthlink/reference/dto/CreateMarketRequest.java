package com.wealthlink.reference.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateMarketRequest {
    private UUID countryId;
    private String name;
    private String micCode;
    private String timezone;
}
