package com.wealthlink.customer.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class IdentifierResponse {
    private UUID id;
    private UUID customerId;
    private String idType;
    private UUID issuingCountryId;
}
