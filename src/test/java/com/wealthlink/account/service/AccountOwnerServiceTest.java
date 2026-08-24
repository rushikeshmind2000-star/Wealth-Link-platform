package com.wealthlink.account.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountOwnerServiceTest {

    private AccountOwnerService accountOwnerService;

    @BeforeEach
    void setUp() {
        accountOwnerService = new AccountOwnerService();
    }

    @Test
    void testGetAll() {
        List<Object> result = accountOwnerService.getAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetById() {
        UUID id = UUID.randomUUID();
        Object result = accountOwnerService.getById(id);
        assertNull(result);
    }
}
