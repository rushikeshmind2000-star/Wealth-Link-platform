package com.wealthlink.account.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateAccountRequest {
    private String accountNumber;
    private String accountType;
    private UUID currencyId;
    private UUID countryId;
}
