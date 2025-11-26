package com.example.farmvibe_backand_new.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private String description;
    private String category_image_url;

    // Optional: list of products under this category
    private List<ProductDetailsDTO> products;
}
