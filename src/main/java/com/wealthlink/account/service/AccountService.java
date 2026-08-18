package com.wealthlink.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AccountService {

    public List<Object> getAll() {
        return new ArrayList<>(); // TODO: Implement
    }

    public Object getById(UUID id) {
        return new Object(); // TODO: Implement
    }
}
