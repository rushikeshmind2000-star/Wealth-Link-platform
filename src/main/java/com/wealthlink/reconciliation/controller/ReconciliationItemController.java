package com.wealthlink.reconciliation.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.wealthlink.reconciliation.dto.ReconItemResponse;
import com.wealthlink.reconciliation.dto.ResolveReconRequest;

@Tag(name = "Dev 4 - Dividends, Reconciliation & Audit", description = "Downstream processes")
@Tag(name = "Reconciliation APIs", description = "Maintained by: Mayur Mali")
@RestController
@RequiredArgsConstructor
public class ReconciliationItemController {

    @GetMapping("/api/reconciliation/items/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<ReconItemResponse> getItem(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/reconciliation/items/{id}/resolve")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ReconItemResponse> resolveItem(@PathVariable UUID id, @RequestBody ResolveReconRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

}
