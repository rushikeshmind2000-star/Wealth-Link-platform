package com.wealthlink.account.dto;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class AccountResponse {
    private UUID id;
    private String accountNumber;
    private String accountType;
    private String currency; // NOK
    private String country; // NO
    private String status;
    private Instant openedAt;
}
