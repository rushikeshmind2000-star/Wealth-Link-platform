package com.wealthlink.ledger.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.wealthlink.ledger.dto.JournalResponse;
import com.wealthlink.ledger.dto.ReverseJournalRequest;
import com.wealthlink.ledger.dto.ReverseJournalResponse;

@Tag(name = "Dev 3 - Portfolio, Trading & Ledger", description = "Core money-movement path")
@Tag(name = "Ledger APIs", description = "Maintained by: Rushikesh Mind")
@RestController
@RequiredArgsConstructor
public class JournalController {

    @GetMapping("/api/journals")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<JournalResponse>> listJournals() {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/journals/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<JournalResponse> getJournal(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/journals/{id}/entries")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Object>> listJournalEntries(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/journals/{id}/reverse")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ReverseJournalResponse> reverseJournal(@PathVariable UUID id, @RequestBody ReverseJournalRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

}
