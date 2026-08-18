package com.wealthlink.reconciliation.dto;

import lombok.Data;

@Data
public class ResolveReconRequest {
    private String resolutionType;
    private String resolutionNotes;
}
