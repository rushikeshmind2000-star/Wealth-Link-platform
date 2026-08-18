package com.wealthlink.customer.dto;

import lombok.Data;

@Data
public class AddContactRequest {
    private String contactType;
    private String value;
    private Boolean isPrimary;
}
