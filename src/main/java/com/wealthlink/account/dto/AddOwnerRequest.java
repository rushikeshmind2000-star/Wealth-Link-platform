package com.wealthlink.account.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class AddOwnerRequest {
    private UUID customerId;
    private String ownershipRole;
}
