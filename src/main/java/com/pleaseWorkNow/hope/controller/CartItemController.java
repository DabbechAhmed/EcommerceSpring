package com.pleaseWorkNow.hope.controller;


import com.pleaseWorkNow.hope.exceptions.ResourceNotFoundException;
import com.pleaseWorkNow.hope.model.Cart;
import com.pleaseWorkNow.hope.model.User;
import com.pleaseWorkNow.hope.response.ApiResponse;
import com.pleaseWorkNow.hope.service.cart.CartService;
import com.pleaseWorkNow.hope.service.cart.ICartItemService;
import com.pleaseWorkNow.hope.service.cart.ICartService;
import com.pleaseWorkNow.hope.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cart-items")
public class CartItemController {
    private final ICartService cartService;
    private final ICartItemService cartItemService;
    private final IUserService userService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long productId, @RequestParam Integer quantity) {
        try {
            User user = userService.getUserById(1L);
            Cart cart = cartService.initializeCart(user);

            cartItemService.addCarItem(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Item added to cart successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/remove/{cartId}/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        try {
            cartItemService.removeCartItem(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse("Item removed from cart successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update-quantity/{cartId}/{itemId}")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId, @RequestParam Integer quantity) {
        try {
            cartItemService.upadateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new ApiResponse("Item quantity updated successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
