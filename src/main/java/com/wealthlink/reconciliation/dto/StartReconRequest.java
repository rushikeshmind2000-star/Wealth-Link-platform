package com.wealthlink.reconciliation.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class StartReconRequest {
    private String runType;
    private LocalDate businessDate;
    private UUID providerId;
}
