package com.example.farmvibe_backand_new.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {
    private Long addressId;
    private String street;
    // Add other address fields as needed
}
