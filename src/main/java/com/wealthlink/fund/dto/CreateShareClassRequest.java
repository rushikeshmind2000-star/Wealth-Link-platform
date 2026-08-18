package com.wealthlink.fund.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CreateShareClassRequest {
    private String classCode;
    private UUID currencyId;
    private String shareClassIsin;
}
