package com.wealthlink.reference.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.wealthlink.reference.dto.CreateCountryRequest;
import com.wealthlink.reference.dto.CountryResponse;

@Tag(name = "Reference Data APIs", description = "Maintained by: Kuldeep Pachori")
@Tag(name = "Reference Data APIs", description = "Maintained by: Kuldeep Pachori")
@RestController
@RequiredArgsConstructor
public class CountryController {

    @GetMapping("/api/countries")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<CountryResponse>> listCountries() {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/countries")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CountryResponse> addCountry(@RequestBody CreateCountryRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/countries/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<CountryResponse> getCountry(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

}
