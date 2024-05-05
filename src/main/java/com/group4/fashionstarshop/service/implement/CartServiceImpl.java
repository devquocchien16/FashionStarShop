package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.dto.UserRegisterDTO;
import com.group4.fashionstarshop.model.Cart;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.repository.CartRepository;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Cart createCart(UserRegisterDTO userDTO) {
        Cart cart = new Cart();
        User user = userRepository.findByEmail(userDTO.getEmail());
        cart.setUser(user);
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public Cart findCartByUserId(User user) {
        return cartRepository.findCartByUser(user);

    }

    @Override
    public Cart findById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }
}
