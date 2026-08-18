package com.wealthlink.importdata.dto;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class ImportBatchResponse {
    private UUID id;
    private String status;
    private Integer recordsReceived;
    private Integer recordsProcessed;
    private Integer recordsFailed;
    private Instant startedAt;
    private Instant completedAt;
}
