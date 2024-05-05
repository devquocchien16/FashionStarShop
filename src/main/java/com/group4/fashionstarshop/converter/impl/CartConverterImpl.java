package com.group4.fashionstarshop.converter.impl;

import com.group4.fashionstarshop.converter.CartConverter;
import com.group4.fashionstarshop.dto.CartDTO;
import com.group4.fashionstarshop.model.Cart;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CartConverterImpl implements CartConverter {
    @Override
    public Cart convertDtoToEntity(CartDTO cartDto) {
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartDto, cart);
        return cart;
    }

    @Override
    public CartDTO convertEntityToDto(Cart cart) {
        CartDTO cartDto = new CartDTO();
        BeanUtils.copyProperties(cart, cartDto);
        cartDto.setUserId(cart.getUser().getId());
        return cartDto;
    }
}
