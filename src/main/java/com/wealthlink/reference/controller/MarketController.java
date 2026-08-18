package com.wealthlink.reference.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.wealthlink.reference.dto.CreateMarketRequest;
import com.wealthlink.reference.dto.MarketResponse;
import com.wealthlink.reference.dto.UpdateMarketStatusRequest;

@Tag(name = "Dev 1 - Foundation", description = "Identity, Reference Data, Customer, Account")
@Tag(name = "Reference Data APIs", description = "Maintained by: Kuldeep Pachori")
@RestController
@RequiredArgsConstructor
public class MarketController {

    @GetMapping("/api/markets")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<MarketResponse>> listMarkets() {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/markets")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<MarketResponse> addMarket(@RequestBody CreateMarketRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/markets/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<MarketResponse> getMarket(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PatchMapping("/api/markets/{id}/status")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<MarketResponse> updateMarketStatus(@PathVariable UUID id, @RequestBody UpdateMarketStatusRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

}
