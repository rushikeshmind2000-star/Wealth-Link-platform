package com.wealthlink.importdata.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.wealthlink.importdata.dto.StartImportJobRequest;
import com.wealthlink.importdata.dto.StartImportJobResponse;

@Tag(name = "Dev 2 - Funds & Market Data", description = "Funds, Providers, FX, Pricing, Import")
@Tag(name = "Import APIs", description = "Maintained by: Sanket Ganje")
@RestController
@RequiredArgsConstructor
public class ImportJobController {

    @GetMapping("/api/import/jobs")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Object>> listJobs() {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/import/jobs/{id}/run")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<StartImportJobResponse> runJob(@PathVariable UUID id, @RequestBody StartImportJobRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

}
