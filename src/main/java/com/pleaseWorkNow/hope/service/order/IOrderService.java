package com.pleaseWorkNow.hope.service.order;

import com.pleaseWorkNow.hope.dto.OrderDto;
import com.pleaseWorkNow.hope.model.Order;

import java.util.List;

public interface IOrderService {
    OrderDto placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
