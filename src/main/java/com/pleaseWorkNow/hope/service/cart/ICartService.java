package com.pleaseWorkNow.hope.service.cart;

import com.pleaseWorkNow.hope.dto.CartDto;
import com.pleaseWorkNow.hope.model.Cart;
import com.pleaseWorkNow.hope.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);


    Cart initializeCart(User user);

    CartDto convertToDto(Cart cart);

    Cart getCartByUserId(Long userId);
}
