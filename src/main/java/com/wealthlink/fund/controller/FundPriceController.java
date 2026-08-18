package com.wealthlink.fund.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.wealthlink.fund.dto.FundPriceResponse;
import com.wealthlink.fund.dto.ImportFundPriceRequest;

@Tag(name = "Dev 2 - Funds & Market Data", description = "Funds, Providers, FX, Pricing, Import")
@Tag(name = "Pricing APIs", description = "Maintained by: Sanket Ganje")
@RestController
@RequiredArgsConstructor
public class FundPriceController {

    @GetMapping("/api/fund-prices")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<FundPriceResponse>> listFundPrices() {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/fund-prices")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<FundPriceResponse> addFundPrice(@RequestBody ImportFundPriceRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/fund-prices/latest")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<FundPriceResponse> getLatestNav(@RequestParam("shareClassId") UUID shareClassId) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/fund-prices/as-of")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<FundPriceResponse>> getNavAsOf() {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/fund-prices/import")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> importFundPrices(@RequestBody Object payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/fund-prices/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> getFundPrice(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

}
