package com.wealthlink.reconciliation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReconItemResponse {
    private UUID id;
    private String internalReferenceType;
    private UUID internalReferenceId;
    private String matchStatus;
    private Map<String, Object> differenceDetails;
    private ResolutionResponse resolution;
    
    @Data
    @Builder
    public static class ResolutionResponse {
        private String resolutionType;
        private String resolutionNotes;
        private String resolvedBy;
        private String resolvedAt;
    }
}
