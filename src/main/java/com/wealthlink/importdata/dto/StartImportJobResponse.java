package com.wealthlink.importdata.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class StartImportJobResponse {
    private UUID batchId;
    private String status;
    private String idempotencyKey;
}
