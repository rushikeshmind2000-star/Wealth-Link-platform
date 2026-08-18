package com.wealthlink.customer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {
    private UUID id;
    private String customerNumber;
    private String customerType;
    private UUID countryId;
    private UUID taxResidencyCountryId;
    private String status;
    private Instant createdAt;
    
    private CountryNestedResponse country;
    private CountryNestedResponse taxResidencyCountry;

    @Data
    @Builder
    public static class CountryNestedResponse {
        private UUID id;
        private String isoCode;
        private String name;
    }
}
