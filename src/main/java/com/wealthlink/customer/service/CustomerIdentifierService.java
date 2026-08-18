package com.wealthlink.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomerIdentifierService {

    public List<Object> getAll() {
        return new ArrayList<>();
    }

    public Object getById(UUID id) {
        return null;
    }
}
