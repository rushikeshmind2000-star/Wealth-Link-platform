package com.wealthlink.fund.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.wealthlink.fx.dto.CreateFxRateRequest;
import com.wealthlink.fx.dto.FxRateResponse;
import java.time.LocalDate;

@Tag(name = "Dev 2 - Funds & Market Data", description = "Funds, Providers, FX, Pricing, Import")
@Tag(name = "FX APIs", description = "Maintained by: Sanket Ganje")
@RestController
@RequiredArgsConstructor
public class FxRateController {

    @GetMapping("/api/fx/sources")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Object>> listFxSources() {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/fx/rates")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<FxRateResponse>> listFxRates() {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/fx/rates")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<FxRateResponse> createFxRate(@RequestBody CreateFxRateRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/fx/rates/as-of")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<FxRateResponse> getFxRateAsOf(
            @RequestParam("base") String base,
            @RequestParam("quote") String quote,
            @RequestParam("date") LocalDate date) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

}
