package com.wealthlink.importdata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImportItemResponse {
    private UUID id;
    private String externalReference;
    private String itemType;
    private String status;
    private Map<String, Object> errorDetails;
}
