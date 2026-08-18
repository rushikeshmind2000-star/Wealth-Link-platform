package com.wealthlink.identity.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.wealthlink.identity.dto.LoginRequest;
import com.wealthlink.identity.dto.LoginResponse;
import com.wealthlink.identity.dto.RefreshRequest;
import com.wealthlink.identity.dto.RefreshResponse;

@Tag(name = "Dev 1 - Foundation", description = "Identity, Reference Data, Customer, Account")
@Tag(name = "Identity APIs", description = "Maintained by: Kuldeep Pachori")
@RestController
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/api/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/auth/refresh")
    public ResponseEntity<RefreshResponse> refresh(@RequestBody RefreshRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }
}
