package com.wealthlink.fund.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.wealthlink.fund.dto.*;

@Tag(name = "Dev 2 - Funds & Market Data", description = "Funds, Providers, FX, Pricing, Import")
@Tag(name = "Funds APIs", description = "Maintained by: Sanket Ganje")
@RestController
@RequiredArgsConstructor
public class FundController {

    @GetMapping("/api/funds")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<FundResponse>> listFunds() {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/funds")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<FundResponse> createFund(@RequestBody CreateFundRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/funds/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<FundResponse> getFund(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PutMapping("/api/funds/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<FundResponse> updateFund(@PathVariable UUID id, @RequestBody Object payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/funds/{id}/share-classes")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<ShareClassResponse>> listShareClasses(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/funds/{id}/share-classes")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ShareClassResponse> addShareClass(@PathVariable UUID id, @RequestBody CreateShareClassRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/funds/{id}/providers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Object>> listFundProviders(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/funds/{id}/providers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> addFundProvider(@PathVariable UUID id, @RequestBody Object payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

}
