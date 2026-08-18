package com.wealthlink.dividend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.wealthlink.dividend.dto.CalculateDividendResponse;
import com.wealthlink.dividend.dto.CreateDividendRequest;
import com.wealthlink.dividend.dto.DividendAllocationResponse;
import com.wealthlink.dividend.dto.DividendResponse;

@Tag(name = "Dev 4 - Dividends, Reconciliation & Audit", description = "Downstream processes")
@Tag(name = "Dividends APIs", description = "Maintained by: Mayur Mali")
@RestController
@RequiredArgsConstructor
public class DividendEventController {

    @GetMapping("/api/dividends")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<DividendResponse>> listDividendEvents() {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/dividends")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<DividendResponse> createDividendEvent(@RequestBody CreateDividendRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/dividends/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<DividendResponse> getDividendEvent(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/dividends/{id}/calculate")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CalculateDividendResponse> calculateAllocations(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/dividends/{id}/allocations")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<DividendAllocationResponse>> listAllocations(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/dividends/{id}/allocate")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> executeAllocations(@PathVariable UUID id, @RequestBody Object payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/dividends/{id}/correct")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> correctDividendEvent(@PathVariable UUID id, @RequestBody Object payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

}
