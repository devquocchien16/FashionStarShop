package com.group4.fashionstarshop.converter.impl;

import com.group4.fashionstarshop.converter.CartConverter;
import com.group4.fashionstarshop.converter.VariantConverter;
import com.group4.fashionstarshop.converter.SaveForLaterConverter;
import com.group4.fashionstarshop.dto.CartDTO;
import com.group4.fashionstarshop.dto.SaveForLaterDTO;
import com.group4.fashionstarshop.dto.VariantDTO;
import com.group4.fashionstarshop.model.Cart;
import com.group4.fashionstarshop.model.SaveForLater;
import com.group4.fashionstarshop.model.Variant;
import com.group4.fashionstarshop.service.CartService;
import com.group4.fashionstarshop.service.VariantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SaveForLaterConverterImpl implements SaveForLaterConverter {
    @Autowired
    private CartService cartService; 

    @Autowired
    private CartConverter cartConverter;

    @Autowired
    private VariantConverter variantConverter;
    
    @Autowired
    private VariantService variantService;

    @Override
    public SaveForLater convertDtoToEntity(SaveForLaterDTO saveForLaterDto) {
        SaveForLater saveForLater = new SaveForLater();
        BeanUtils.copyProperties(saveForLaterDto, saveForLater);
        Cart cart = cartService.findById(saveForLaterDto.getCartDto().getId());
//        Variant variant = variantService.findById(saveForLaterDto.getVariantDto().getId());
        saveForLater.setCart(cart);
//        saveForLater.setVariant(variant);
        return saveForLater;
    }

    @Override
    public SaveForLaterDTO convertEntityToDto(SaveForLater saveForLater) {
        SaveForLaterDTO saveForLaterDto = new SaveForLaterDTO();
        Long id = saveForLater.getId();
        int quantity = saveForLater.getQuanity();
        CartDTO cartDto = cartConverter.convertEntityToDto(saveForLater.getCart());
        VariantDTO variantDto = variantConverter.entityToDTO(saveForLater.getVariant());
        saveForLaterDto.setId(id);
        saveForLaterDto.setQuantity(quantity);
        saveForLaterDto.setCartDto(cartDto);
        saveForLaterDto.setVariantDto(variantDto);
        return saveForLaterDto;
    }

    @Override
    public List<SaveForLaterDTO> convertEntitiesToDtos(List<SaveForLater> saveForLaters) {
//        return saveForLaters.stream()
//                .map(this::convertEntityToDto)
//                .collect(Collectors.toList());

        List<SaveForLaterDTO> saveForLaterDTOList = new ArrayList<>();
        for (SaveForLater saveForLater: saveForLaters) {
            SaveForLaterDTO saveForLaterDto = convertEntityToDto(saveForLater);
            saveForLaterDTOList.add(saveForLaterDto);
        }
        return saveForLaterDTOList;
    }

    @Override
    public List<SaveForLater> convertDtoToEntities(List<SaveForLaterDTO> cartLineDtos) {
        return null;
    }
}
