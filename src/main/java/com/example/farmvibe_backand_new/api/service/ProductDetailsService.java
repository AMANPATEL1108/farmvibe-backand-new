package com.example.farmvibe_backand_new.api.service;


import com.example.farmvibe_backand_new.api.dto.ProductDetailsDTO;
import com.example.farmvibe_backand_new.api.model.ProductDetails;
import java.util.List;

public interface ProductDetailsService {

    List<ProductDetails> getAllProducts();

    ProductDetailsDTO getProductById(Long id);

    ProductDetails createProduct(ProductDetails product);

    ProductDetails updateProduct(Long id, ProductDetails updatedProduct);

    void deleteProduct(Long id);

    List<ProductDetailsDTO> getProductsByCategoryId(Long categoryId);
}