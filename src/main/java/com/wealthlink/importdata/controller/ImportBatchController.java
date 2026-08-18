package com.wealthlink.importdata.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.wealthlink.importdata.dto.ImportBatchResponse;
import com.wealthlink.importdata.dto.ImportItemResponse;

@Tag(name = "Dev 2 - Funds & Market Data", description = "Funds, Providers, FX, Pricing, Import")
@Tag(name = "Import APIs", description = "Maintained by: Sanket Ganje")
@RestController
@RequiredArgsConstructor
public class ImportBatchController {

    @GetMapping("/api/import/batches")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<ImportBatchResponse>> listBatches() {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/import/batches/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<ImportBatchResponse> getBatch(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/import/batches/{id}/items")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<ImportItemResponse>> listBatchItems(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/import/batches/{id}/retry")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> retryBatch(@PathVariable UUID id, @RequestBody Object payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

}
