package com.wealthlink.importdata.dto;

import lombok.Data;

@Data
public class StartImportJobRequest {
    private String idempotencyKey;
}
