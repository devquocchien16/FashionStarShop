package com.group4.fashionstarshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group4.fashionstarshop.model.Cart;
import com.group4.fashionstarshop.model.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartByUser(User user);
    Cart findByUserId(Long userId);
}
