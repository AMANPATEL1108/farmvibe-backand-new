package com.example.farmvibe_backand_new.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private int quantity;
    private double totalPrice;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private String paymentMethod;
    private String paymentStatus;
    private String deliveryStatus;
    private boolean orderConfirmed;
    private Long productId;
    private Long categoryId;
    private Long addressId;
    private Long userId;
}