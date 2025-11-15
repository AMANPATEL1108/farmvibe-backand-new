package com.example.farmvibe_backand_new.api.serviceImpl;


import com.example.farmvibe_backand_new.api.model.ProductDetails;
import com.example.farmvibe_backand_new.api.repository.ProductDetailsRepository;
import com.example.farmvibe_backand_new.api.service.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDetailsServiceImpl implements ProductDetailsService {

    private final ProductDetailsRepository repository;

    @Override
    public List<ProductDetails> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public ProductDetails getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
    }

    @Override
    public ProductDetails createProduct(ProductDetails product) {
        return repository.save(product);
    }

    @Override
    public ProductDetails updateProduct(Long id, ProductDetails updatedProduct) {
        ProductDetails existing = getProductById(id);

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

        return repository.save(existing);
    }

    @Override
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}