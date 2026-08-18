package com.wealthlink.dividend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import com.wealthlink.dividend.service.DividendAllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Dev 4 - Dividends, Reconciliation & Audit", description = "Downstream processes")
@Tag(name = "Dividends APIs", description = "Maintained by: Mayur Mali")
@RestController
@RequestMapping("/api/dividendallocations")
@RequiredArgsConstructor
public class DividendAllocationController {

    private final DividendAllocationService service;

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
