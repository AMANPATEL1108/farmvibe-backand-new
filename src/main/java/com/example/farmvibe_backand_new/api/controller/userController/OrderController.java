    package com.example.farmvibe_backand_new.api.controller.userController;

    import com.example.farmvibe_backand_new.api.dto.OrderRequest;
    import com.example.farmvibe_backand_new.api.dto.OrderResponse;
    import com.example.farmvibe_backand_new.api.service.OrderService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("user/api/orders")
    @RequiredArgsConstructor
    public class OrderController {

        private final OrderService orderService;

        @PostMapping
        public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
            OrderResponse orderResponse = orderService.createOrder(orderRequest);
            return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
        }

        @GetMapping("/{orderId}")
        public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
            OrderResponse orderResponse = orderService.getOrderById(orderId);
            return ResponseEntity.ok(orderResponse);
        }

        @GetMapping
        public ResponseEntity<List<OrderResponse>> getAllOrders() {
            List<OrderResponse> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        }

        @PutMapping("/{orderId}")
        public ResponseEntity<OrderResponse> updateOrder(
                @PathVariable Long orderId,
                @RequestBody OrderRequest orderRequest) {
            OrderResponse orderResponse = orderService.updateOrder(orderId, orderRequest);
            return ResponseEntity.ok(orderResponse);
        }

        @DeleteMapping("/{orderId}")
        public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
            orderService.deleteOrder(orderId);
            return ResponseEntity.noContent().build();
        }

        @GetMapping("/user/{userId}")
        public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable Long userId) {
            List<OrderResponse> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(orders);
        }
    }