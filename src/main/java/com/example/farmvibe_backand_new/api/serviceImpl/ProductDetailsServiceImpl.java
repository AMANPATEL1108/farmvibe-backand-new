package com.example.farmvibe_backand_new.api.serviceImpl;


import com.example.farmvibe_backand_new.api.dto.ProductDetailsDTO;
import com.example.farmvibe_backand_new.api.model.ProductDetails;
import com.example.farmvibe_backand_new.api.repository.ProductDetailsRepository;
import com.example.farmvibe_backand_new.api.service.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDetailsServiceImpl implements ProductDetailsService {

    private final ProductDetailsRepository productDetailsRepository;

    @Override
    public List<ProductDetails> getAllProducts() {
        return productDetailsRepository.findAll();
    }

    @Override
    public ProductDetailsDTO getProductById(Long id) {
        ProductDetails p = productDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));

        return new ProductDetailsDTO(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getImageUrl(),
                p.getPrice(),
                p.getWeight(),
                p.getStock()
        );
    }


    @Override
    public ProductDetails createProduct(ProductDetails product) {
        return productDetailsRepository.save(product);
    }

    @Override
    public ProductDetails updateProduct(Long id, ProductDetails updatedProduct) {
        ProductDetails existing = productDetailsRepository.getProductById(id);

        // Null-safe update
        if (updatedProduct.getName() != null)
            existing.setName(updatedProduct.getName());

        if (updatedProduct.getDescription() != null)
            existing.setDescription(updatedProduct.getDescription());

        if (updatedProduct.getImageUrl() != null)
            existing.setImageUrl(updatedProduct.getImageUrl());

        if (updatedProduct.getPrice() != 0)
            existing.setPrice(updatedProduct.getPrice());

        if (updatedProduct.getWeight() != null)
            existing.setWeight(updatedProduct.getWeight());

        if (updatedProduct.getStock() != 0)
            existing.setStock(updatedProduct.getStock());

        if (updatedProduct.getBenefits() != null)
            existing.setBenefits(updatedProduct.getBenefits());

        if (updatedProduct.getCategory() != null)
            existing.setCategory(updatedProduct.getCategory());

        return productDetailsRepository.save(existing);
    }

    @Override
    public void deleteProduct(Long id) {
        productDetailsRepository.deleteById(id);
    }


    @Override
    public List<ProductDetailsDTO> getProductsByCategoryId(Long categoryId) {
        List<ProductDetails> products = productDetailsRepository.findByCategoryId(categoryId);

        // Map entity to DTO
        return products.stream()
                .map(product -> new ProductDetailsDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getImageUrl(),
                        product.getPrice(),
                        product.getWeight(),
                        product.getStock()
                ))
                .toList();
    }

}