package com.wealthlink.identity.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import com.wealthlink.identity.dto.CreateUserRequest;
import com.wealthlink.identity.dto.UpdateUserRequest;
import com.wealthlink.identity.dto.UpdateUserRolesRequest;
import com.wealthlink.identity.dto.AppUserDto;
import com.wealthlink.identity.service.AppUserService;

@Tag(name = "Dev 1 - Foundation", description = "Identity, Reference Data, Customer, Account")
@Tag(name = "Identity APIs", description = "Maintained by: Kuldeep Pachori")
@RestController
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping("/api/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Object>> listUsers() {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PostMapping("/api/users")
    public ResponseEntity<AppUserDto> createUser(@RequestBody CreateUserRequest payload) {
        return ResponseEntity.ok(appUserService.createUser(payload));
    }

    @GetMapping("/api/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PutMapping("/api/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> updateUser(@PathVariable UUID id, @RequestBody UpdateUserRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

    @PutMapping("/api/users/{id}/roles")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> updateUserRoles(@PathVariable UUID id, @RequestBody UpdateUserRolesRequest payload) {
        return ResponseEntity.ok().build(); // TODO: Delegate to Service
    }

}
