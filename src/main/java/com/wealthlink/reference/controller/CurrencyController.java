package com.wealthlink.reference.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.wealthlink.reference.dto.CreateCurrencyRequest;
import com.wealthlink.reference.dto.CurrencyResponse;

@Tag(name = "Reference Data APIs", description = "Maintained by: Kuldeep Pachori")
@Tag(name = "Reference Data APIs", description = "Maintained by: Kuldeep Pachori")
@RestController
@RequiredArgsConstructor
public class CurrencyController {

    @GetMapping("/api/currencies")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<CurrencyResponse>> listCurrencies() {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/currencies")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CurrencyResponse> addCurrency(@RequestBody CreateCurrencyRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/currencies/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<CurrencyResponse> getCurrency(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

}
