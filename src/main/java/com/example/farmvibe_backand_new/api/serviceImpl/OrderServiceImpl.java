package com.example.farmvibe_backand_new.api.serviceImpl;

import com.example.farmvibe_backand_new.api.dto.*;
import com.example.farmvibe_backand_new.api.model.*;
import com.example.farmvibe_backand_new.api.repository.*;
import com.example.farmvibe_backand_new.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductDetailsRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // Fetch related entities
        ProductDetails product = productRepository.findById(orderRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Category category = categoryRepository.findById(orderRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Address address = addressRepository.findById(orderRequest.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create and save order
        Order order = Order.builder()
                .quantity(orderRequest.getQuantity())
                .totalPrice(orderRequest.getTotalPrice())
                .orderDate(orderRequest.getOrderDate())
                .deliveryDate(orderRequest.getDeliveryDate())
                .paymentMethod(orderRequest.getPaymentMethod())
                .paymentStatus(orderRequest.getPaymentStatus())
                .deliveryStatus(orderRequest.getDeliveryStatus())
                .orderConfirmed(orderRequest.isOrderConfirmed())
                .product(product)
                .category(category)
                .address(address)
                .user(user)
                .build();

        Order savedOrder = orderRepository.save(order);
        return convertToResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        return convertToResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateOrder(Long orderId, OrderRequest orderRequest) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Fetch related entities if they're being updated
        ProductDetails product = existingOrder.getProduct();
        if (orderRequest.getProductId() != null &&
                !orderRequest.getProductId().equals(product.getId())) {
            product = productRepository.findById(orderRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
        }

        Category category = existingOrder.getCategory();
        if (orderRequest.getCategoryId() != null &&
                !orderRequest.getCategoryId().equals(category.getId())) {
            category = categoryRepository.findById(orderRequest.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }

        Address address = existingOrder.getAddress();
        if (orderRequest.getAddressId() != null &&
                !orderRequest.getAddressId().equals(address.getAddress_id())) {
            address = addressRepository.findById(orderRequest.getAddressId())
                    .orElseThrow(() -> new RuntimeException("Address not found"));
        }

        User user = existingOrder.getUser();
        if (orderRequest.getUserId() != null &&
                !orderRequest.getUserId().equals(user.getUser_id())) {
            user = userRepository.findById(orderRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }

        // Update order
        existingOrder.setQuantity(orderRequest.getQuantity());
        existingOrder.setTotalPrice(orderRequest.getTotalPrice());
        existingOrder.setOrderDate(orderRequest.getOrderDate());
        existingOrder.setDeliveryDate(orderRequest.getDeliveryDate());
        existingOrder.setPaymentMethod(orderRequest.getPaymentMethod());
        existingOrder.setPaymentStatus(orderRequest.getPaymentStatus());
        existingOrder.setDeliveryStatus(orderRequest.getDeliveryStatus());
        existingOrder.setOrderConfirmed(orderRequest.isOrderConfirmed());
        existingOrder.setProduct(product);
        existingOrder.setCategory(category);
        existingOrder.setAddress(address);
        existingOrder.setUser(user);

        Order updatedOrder = orderRepository.save(existingOrder);
        return convertToResponse(updatedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        orderRepository.delete(order);
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        // Update this line to use the new method name
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private OrderResponse convertToResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .orderDate(order.getOrderDate())
                .deliveryDate(order.getDeliveryDate())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .deliveryStatus(order.getDeliveryStatus())
                .orderConfirmed(order.isOrderConfirmed())
                .product(ProductResponse.builder()
                        .productId(order.getProduct().getId())
                        .productName(order.getProduct().getName())
                        .build())
                .category(CategoryResponse.builder()
                        .categoryId(order.getCategory().getId())
                        .categoryName(order.getCategory().getName())
                        .build())
                .address(AddressResponse.builder()
                        .addressId(order.getAddress().getAddress_id())
                        .street(order.getAddress().getStreet())
                        .build())
                .user(UserResponse.builder()
                        .userId(order.getUser().getUser_id())
                        .username(order.getUser().getUsername())
                        .email(order.getUser().getUser_email())
                        .build())
                .build();
    }
}