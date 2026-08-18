package com.wealthlink.customer.service;

import com.wealthlink.customer.dto.CreateCustomerRequest;
import com.wealthlink.customer.dto.CustomerResponse;
import com.wealthlink.customer.entity.Customer;
import com.wealthlink.customer.entity.CustomerStatus;
import com.wealthlink.customer.entity.CustomerType;
import com.wealthlink.customer.repository.CustomerRepository;
import com.wealthlink.reference.entity.Country;
import com.wealthlink.reference.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CountryRepository countryRepository;

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(UUID id) {
        return customerRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Country not found"));
                
        Country taxCountry = countryRepository.findById(request.getTaxResidencyCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Tax Residency Country not found"));

        Customer customer = Customer.builder()
                .customerNumber(request.getCustomerNumber())
                .customerType(CustomerType.valueOf(request.getCustomerType()))
                .status(CustomerStatus.PENDING_KYC)
                .country(country)
                .taxResidencyCountry(taxCountry)
                .build();

        Customer savedCustomer = customerRepository.save(customer);
        return mapToDto(savedCustomer);
    }
    
    @Transactional
    public CustomerResponse updateCustomer(UUID id, Object payload) {
        // Just a stub for update, returning the entity as is for now
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        return mapToDto(customerRepository.save(customer));
    }

    private CustomerResponse mapToDto(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .customerNumber(customer.getCustomerNumber())
                .customerType(customer.getCustomerType().name())
                .status(customer.getStatus().name())
                .countryId(customer.getCountry().getId())
                .taxResidencyCountryId(customer.getTaxResidencyCountry().getId())
                .createdAt(customer.getCreatedAt())
                .country(CustomerResponse.CountryNestedResponse.builder()
                        .id(customer.getCountry().getId())
                        .isoCode(customer.getCountry().getIsoCode())
                        .name(customer.getCountry().getName())
                        .build())
                .taxResidencyCountry(CustomerResponse.CountryNestedResponse.builder()
                        .id(customer.getTaxResidencyCountry().getId())
                        .isoCode(customer.getTaxResidencyCountry().getIsoCode())
                        .name(customer.getTaxResidencyCountry().getName())
                        .build())
                .build();
    }
}
