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
public class OrderResponse {
    private Long orderId;
    private int quantity;
    private double totalPrice;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private String paymentMethod;
    private String paymentStatus;
    private String deliveryStatus;
    private boolean orderConfirmed;
    private ProductResponse product;
    private CategoryResponse category;
    private AddressResponse address;
    private UserResponse user;
}

