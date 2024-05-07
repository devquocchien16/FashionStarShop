package com.group4.fashionstarshop.converter.impl;

import com.group4.fashionstarshop.converter.CartConverter;
import com.group4.fashionstarshop.converter.CartLineConverter;
import com.group4.fashionstarshop.converter.VariantConverter;
import com.group4.fashionstarshop.dto.CartDTO;
import com.group4.fashionstarshop.dto.CartLineDTO;
import com.group4.fashionstarshop.dto.VariantDTO;
import com.group4.fashionstarshop.model.Cart;
import com.group4.fashionstarshop.model.CartLine;
import com.group4.fashionstarshop.model.Variant;
import com.group4.fashionstarshop.repository.VariantRepository;
import com.group4.fashionstarshop.service.CartService;
import com.group4.fashionstarshop.service.VariantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartLineConverterImpl implements CartLineConverter {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartConverter cartConverter;

    @Autowired
    private VariantConverter variantConverter;
 
    @Autowired
    private VariantService variantService;

    @Override
    public CartLine convertDtoToEntity(CartLineDTO cartLineDto) {
        CartLine cartLine = new CartLine();
        BeanUtils.copyProperties(cartLineDto, cartLine);
        Cart cart = cartService.findById(cartLineDto.getCartDto().getId());
        Variant variant = variantService.findById(cartLineDto.getVariantDto().getId());
        cartLine.setCart(cart);
        cartLine.setVariant(variant);
        return cartLine;
    }

    @Override
    public CartLineDTO convertEntityToDto(CartLine cartLine) {
        CartLineDTO cartLineDto = new CartLineDTO();
        BeanUtils.copyProperties(cartLine, cartLineDto);
        CartDTO cartDto = cartConverter.convertEntityToDto(cartLine.getCart());
        VariantDTO variantDto = variantConverter.entityToDTO(cartLine.getVariant());
        cartLineDto.setCartDto(cartDto);
        cartLineDto.setVariantDto(variantDto);
        return cartLineDto;
    }

    @Override
    public List<CartLineDTO> convertEntitiesToDtos(List<CartLine> cartLines) {
        return cartLines.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override

    public List<CartLine> convertDtoToEntities(List<CartLineDTO> cartLineDTOS) {
        return null;
    }
}
