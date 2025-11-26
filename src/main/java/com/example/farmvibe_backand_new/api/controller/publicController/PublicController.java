package com.example.farmvibe_backand_new.api.controller.publicController;

import com.example.farmvibe_backand_new.api.dto.ProductDetailsDTO;
import com.example.farmvibe_backand_new.api.model.ProductDetails;
import com.example.farmvibe_backand_new.api.service.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("public/api/products")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
@RequiredArgsConstructor
public class PublicController {

    private final ProductDetailsService productService;

    @GetMapping("/get-all-products")
    public ResponseEntity<List<ProductDetailsDTO>> getAllProducts() {

        List<ProductDetailsDTO> dtos = productService.getAllProducts()
                .stream()
                .map(p -> new ProductDetailsDTO(
                        p.getId(),
                        p.getName(),
                        p.getDescription(),
                        p.getImageUrl(),
                        p.getPrice(),
                        p.getWeight(),
                        p.getStock()
                ))
                .toList();

        System.out.println("✅ Returning " + dtos.size() + " products");
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/get-product/{id}")
    public ResponseEntity<ProductDetails> getProductById(@PathVariable Long id) {
        System.out.println("🎯 GET /public/api/products/get-product/" + id + " called");
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/category-by-products/{categoryId}")
    public ResponseEntity<List<ProductDetailsDTO>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<ProductDetailsDTO> products = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }
}