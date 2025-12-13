package com.example.farmvibe_backand_new.api.service;

import com.example.farmvibe_backand_new.api.dto.OrderRequest;
import com.example.farmvibe_backand_new.api.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(Long orderId);
    List<OrderResponse> getAllOrders();
    OrderResponse updateOrder(Long orderId, OrderRequest orderRequest);
    void deleteOrder(Long orderId);
    List<OrderResponse> getOrdersByUserId(Long userId);
}