package com.pleaseWorkNow.hope.controller;

import com.pleaseWorkNow.hope.dto.OrderDto;
import com.pleaseWorkNow.hope.exceptions.ResourceNotFoundException;
import com.pleaseWorkNow.hope.model.Order;
import com.pleaseWorkNow.hope.response.ApiResponse;
import com.pleaseWorkNow.hope.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
        try {
            OrderDto order=orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Order created successfully", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse> getOrders(@PathVariable Long orderId){
        try {
            OrderDto order=orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Orders retrieved successfully",order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error!", e.getMessage()));
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getOrdersByUserId(@PathVariable Long userId){
        try {
            List<OrderDto> orders=orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Orders retrieved successfully",orders));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error!", e.getMessage()));
        }
    }


}
