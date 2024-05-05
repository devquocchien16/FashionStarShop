package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.CartLineDTO;
import com.group4.fashionstarshop.model.CartLine;

import java.util.List;

public interface CartLineConverter {
    CartLine convertDtoToEntity(CartLineDTO cartLineDto);
    CartLineDTO convertEntityToDto(CartLine cartLine);
    List<CartLineDTO> convertEntitiesToDtos(List<CartLine> cartLines);
    List<CartLine> convertDtoToEntities(List<CartLineDTO> cartLineDTOS);
}
