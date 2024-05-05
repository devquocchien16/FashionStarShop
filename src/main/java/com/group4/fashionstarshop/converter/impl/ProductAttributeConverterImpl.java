package com.group4.fashionstarshop.converter.impl;

import com.group4.fashionstarshop.converter.ProductAttributeConverter;
import com.group4.fashionstarshop.dto.ProductAttributeDTO;
import com.group4.fashionstarshop.model.Attribute;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductAttributeConverterImpl implements ProductAttributeConverter {
    @Override
    public List<ProductAttributeDTO> entitiesToDTOs(List<Attribute> element) {
        return element.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public ProductAttributeDTO entityToDTO(Attribute element) {
        ProductAttributeDTO result = new ProductAttributeDTO();
        BeanUtils.copyProperties(element, result);
        return result;
    }
    @Override
    public Attribute dtoToEntity(ProductAttributeDTO element) {
        Attribute result = new Attribute();
        BeanUtils.copyProperties(element, result);
        return result;
    }
}
