package com.wealthlink.reconciliation.dto;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class StartReconResponse {
    private UUID id;
    private String runType;
    private LocalDate businessDate;
    private String status;
    private Instant startedAt;
}
