package com.wealthlink.customer.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateCustomerRequest {
    private String customerNumber;
    private String customerType;
    private UUID countryId;
    private UUID taxResidencyCountryId;
}
