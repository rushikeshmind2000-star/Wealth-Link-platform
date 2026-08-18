package com.wealthlink.trade.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.wealthlink.trade.dto.CreateSettlementRequest;
import com.wealthlink.trade.dto.RetrySettlementResponse;
import com.wealthlink.trade.dto.SettlementResponse;

@Tag(name = "Dev 3 - Portfolio, Trading & Ledger", description = "Core money-movement path")
@Tag(name = "Settlement APIs", description = "Maintained by: Rushikesh Mind")
@RestController
@RequiredArgsConstructor
public class SettlementController {

    @GetMapping("/api/settlements")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<SettlementResponse>> listSettlements() {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/settlements")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<SettlementResponse> createSettlement(@RequestBody CreateSettlementRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/settlements/{id}/retry")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<RetrySettlementResponse> retrySettlement(@PathVariable UUID id, @RequestBody Object payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

}
