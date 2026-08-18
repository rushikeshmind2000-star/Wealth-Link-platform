package com.wealthlink.customer.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.wealthlink.customer.dto.*;
import com.wealthlink.customer.service.CustomerService;

@Tag(name = "Dev 1 - Foundation", description = "Identity, Reference Data, Customer, Account")
@Tag(name = "Customer APIs", description = "Maintained by: Kuldeep Pachori")
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/api/customers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<CustomerResponse>> listCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PostMapping("/api/customers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest payload) {
        return ResponseEntity.ok(customerService.createCustomer(payload));
    }

    @GetMapping("/api/customers/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PutMapping("/api/customers/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable UUID id, @RequestBody Object payload) {
        return ResponseEntity.ok(customerService.updateCustomer(id, payload));
    }

    @GetMapping("/api/customers/{id}/contacts")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<ContactResponse>> listContacts(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/customers/{id}/contacts")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ContactResponse> addContact(@PathVariable UUID id, @RequestBody AddContactRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @GetMapping("/api/customers/{id}/identifiers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<List<IdentifierResponse>> listIdentifiers(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/customers/{id}/identifiers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<IdentifierResponse> addIdentifier(@PathVariable UUID id, @RequestBody AddIdentifierRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

}
