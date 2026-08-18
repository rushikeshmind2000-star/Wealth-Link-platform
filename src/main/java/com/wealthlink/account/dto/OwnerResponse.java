package com.wealthlink.account.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class OwnerResponse {
    private UUID id;
    private UUID accountId;
    private UUID customerId;
    private String ownershipRole;
}
