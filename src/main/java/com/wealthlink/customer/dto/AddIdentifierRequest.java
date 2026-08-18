package com.wealthlink.customer.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class AddIdentifierRequest {
    private String idType;
    private String idValue;
    private UUID issuingCountryId;
}
