package com.group4.fashionstarshop.service;

import com.group4.fashionstarshop.dto.UserRegisterDTO;
import com.group4.fashionstarshop.model.Cart;
import com.group4.fashionstarshop.model.User;


public interface CartService {
    Cart createCart (UserRegisterDTO userDTO);
    Cart findCartByUserId (User user);
    Cart findById(Long id);
}
