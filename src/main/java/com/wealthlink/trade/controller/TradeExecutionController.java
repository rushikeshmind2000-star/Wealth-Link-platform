package com.wealthlink.trade.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.wealthlink.trade.dto.ExecutionResponse;
import com.wealthlink.trade.dto.RecordExecutionRequest;

@Tag(name = "Dev 3 - Portfolio, Trading & Ledger", description = "Core money-movement path")
@Tag(name = "Trading APIs", description = "Maintained by: Rushikesh Mind")
@RestController
@RequiredArgsConstructor
public class TradeExecutionController {

    @PostMapping("/api/executions")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ExecutionResponse> recordExecution(@RequestBody RecordExecutionRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/executions/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<ExecutionResponse> getExecution(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

}
