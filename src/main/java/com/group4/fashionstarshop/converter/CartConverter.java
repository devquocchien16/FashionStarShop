package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.CartDTO;
import com.group4.fashionstarshop.model.Cart;

public interface CartConverter {
    Cart convertDtoToEntity(CartDTO cartDto);
    CartDTO convertEntityToDto(Cart cart);
}
