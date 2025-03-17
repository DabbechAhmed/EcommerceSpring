package com.pleaseWorkNow.hope.repository;

import com.pleaseWorkNow.hope.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);

}
