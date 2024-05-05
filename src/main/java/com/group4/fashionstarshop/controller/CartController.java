package com.group4.fashionstarshop.controller;

import com.group4.fashionstarshop.dto.UserRegisterDTO;
import com.group4.fashionstarshop.model.Cart;
import com.group4.fashionstarshop.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/cart")
@AllArgsConstructor
public class CartController {
	@Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<?> createCart(@RequestBody UserRegisterDTO userDTO){
        Cart cart = cartService.createCart(userDTO);
        return ResponseEntity.ok(cart);
    }
}
