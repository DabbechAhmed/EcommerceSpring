package com.pleaseWorkNow.hope.service.cart;

import com.pleaseWorkNow.hope.model.CartItem;

public interface ICartItemService {
    void addCarItem(Long cartId, Long productId, int quantity);
    void removeCartItem(Long cartId, Long productId);
    void upadateItemQuantity(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
