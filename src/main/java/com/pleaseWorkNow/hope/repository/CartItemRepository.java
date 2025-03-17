package com.pleaseWorkNow.hope.repository;

import com.pleaseWorkNow.hope.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(Long id);
}
