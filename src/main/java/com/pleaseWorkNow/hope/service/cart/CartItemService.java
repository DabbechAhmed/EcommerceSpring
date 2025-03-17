package com.pleaseWorkNow.hope.service.cart;

import com.pleaseWorkNow.hope.model.Cart;
import com.pleaseWorkNow.hope.model.CartItem;
import com.pleaseWorkNow.hope.model.Product;
import com.pleaseWorkNow.hope.repository.CartItemRepository;
import com.pleaseWorkNow.hope.repository.CartRepository;
import com.pleaseWorkNow.hope.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;
    @Override
    public void addCarItem(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> {
                    return item.getProduct().getId().equals(productId);
                })
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    return newItem;
                });
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setTotalPrice();

        cart.addItem(cartItem);  // Automatically updates totalAmount
        cartRepository.save(cart);  // Cascade saves CartItem
    }

    @Override
    public void removeCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem cartItem = getCartItem(cartId, productId);
        cart.removeItem(cartItem);
        cartRepository.save(cart);

    }

    @Override
    public void upadateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item->{
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });


        cart.updateTotalAmount();
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));
    }
}
