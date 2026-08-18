package com.wealthlink.account.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.wealthlink.account.dto.*;

@Tag(name = "Account APIs", description = "Maintained by: Kuldeep Pachori")
@Tag(name = "Dev 1 - Foundation", description = "Identity, Reference Data, Customer, Account")
@Tag(name = "Account APIs", description = "Maintained by: Kuldeep Pachori")
@RestController
@RequiredArgsConstructor
public class AccountController {

    @GetMapping("/api/accounts")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<AccountResponse>> listAccounts() {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/accounts")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody CreateAccountRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/accounts/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PutMapping("/api/accounts/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable UUID id, @RequestBody Object payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/accounts/{id}/owners")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<OwnerResponse>> listOwners(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/accounts/{id}/owners")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<OwnerResponse> addOwner(@PathVariable UUID id, @RequestBody AddOwnerRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PatchMapping("/api/accounts/{id}/status")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AccountResponse> updateAccountStatus(@PathVariable UUID id, @RequestBody Object payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

}
