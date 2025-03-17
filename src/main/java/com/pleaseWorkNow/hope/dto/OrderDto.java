package com.pleaseWorkNow.hope.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private LocalDate orderDate;
    private String status;
    private BigDecimal totalAmount;
    private List<OrderItemDto> orderItems;

}
