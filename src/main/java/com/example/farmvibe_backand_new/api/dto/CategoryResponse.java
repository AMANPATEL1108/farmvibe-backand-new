package com.example.farmvibe_backand_new.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {
    private Long categoryId;
    private String categoryName;
    // Add other category fields as needed
}
