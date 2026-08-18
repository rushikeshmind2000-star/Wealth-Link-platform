package com.wealthlink.reconciliation.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import com.wealthlink.reconciliation.service.ReconciliationResolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Dev 4 - Dividends, Reconciliation & Audit", description = "Downstream processes")
@Tag(name = "Reconciliation APIs", description = "Maintained by: Mayur Mali")
@RestController
@RequestMapping("/api/reconciliationresolutions")
@RequiredArgsConstructor
public class ReconciliationResolutionController {

    private final ReconciliationResolutionService service;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Object>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
