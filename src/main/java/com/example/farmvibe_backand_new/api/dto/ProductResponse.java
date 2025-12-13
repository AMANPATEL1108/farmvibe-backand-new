package com.example.farmvibe_backand_new.api.dto;

import lombok.Builder;
import lombok.Data;

// Additional DTOs for related entities
@Data
@Builder
public class ProductResponse {
    private Long productId;
    private String productName;
    // Add other product fields as needed
}
