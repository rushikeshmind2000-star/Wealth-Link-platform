package com.wealthlink.audit.dto;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class AuditResponse {
    private UUID id;
    private String action;
    private String entityType;
    private UUID entityId;
    private UUID userId;
    private Instant occurredAt;
    private String correlationId;
}
