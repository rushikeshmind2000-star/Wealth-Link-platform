package com.wealthlink.fund.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class ShareClassResponse {
    private UUID id;
    private UUID fundId;
    private String classCode;
    private String currency;
    private String shareClassIsin;
    private String status;
}
