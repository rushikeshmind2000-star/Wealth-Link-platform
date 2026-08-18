package com.wealthlink.identity.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateUserRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;
}
