package com.wealthlink.customer.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class ContactResponse {
    private UUID id;
    private UUID customerId;
    private String contactType;
    private String value;
    private Boolean isPrimary;
}
