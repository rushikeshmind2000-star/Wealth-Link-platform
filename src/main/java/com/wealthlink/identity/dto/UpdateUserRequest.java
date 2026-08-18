package com.wealthlink.identity.dto;

import lombok.Data;
import java.util.List;

@Data
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Boolean active;
}
