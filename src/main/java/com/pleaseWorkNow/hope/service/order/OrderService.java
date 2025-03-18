package com.pleaseWorkNow.hope.service.order;

import com.pleaseWorkNow.hope.dto.OrderDto;
import com.pleaseWorkNow.hope.enums.OrderStatus;
import com.pleaseWorkNow.hope.exceptions.ResourceNotFoundException;
import com.pleaseWorkNow.hope.model.Cart;
import com.pleaseWorkNow.hope.model.Order;
import com.pleaseWorkNow.hope.model.OrderItem;
import com.pleaseWorkNow.hope.model.Product;
import com.pleaseWorkNow.hope.repository.OrderRepository;
import com.pleaseWorkNow.hope.repository.ProductRepository;
import com.pleaseWorkNow.hope.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final ProductRepository productRepository;
    private final ICartService cartService;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDto placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalPrice(orderItems));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return convertToDto(savedOrder);
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;

    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(order, product, cartItem.getQuantity(), cartItem.getTotalPrice());
        }).toList();
    }

    private BigDecimal calculateTotalPrice(List<OrderItem> orderItemList) {
        return orderItemList.stream()
                .map(orderItem -> orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Order not found with id: " + orderId)
                );
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .toList();
    }

    private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
