package com.example.farmvibe_backand_new.api.repository;

import com.example.farmvibe_backand_new.api.dto.CategoryDTO;
import com.example.farmvibe_backand_new.api.model.Category;
import com.example.farmvibe_backand_new.api.model.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long> {
    List<ProductDetails> findByCategoryId(Long categoryId);

    ProductDetails getProductById(Long id);

    @Query("SELECT p.category FROM ProductDetails p WHERE p.id = :productId")
    Optional<Category> findCategoryByProductId(@Param("productId") Long productId);
}