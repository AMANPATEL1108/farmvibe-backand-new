package com.example.farmvibe_backand_new.api.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private String weight;
    private int stock;

}
