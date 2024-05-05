package com.group4.fashionstarshop.converter.impl;

import com.group4.fashionstarshop.converter.AttributeConverter;
import com.group4.fashionstarshop.dto.AttributeDTO;
import com.group4.fashionstarshop.model.Attribute;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AttributeConverterImpl implements AttributeConverter {
    @Override
    public List<AttributeDTO> entitiesToDTOs(List<Attribute> element) {
        return element.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public AttributeDTO entityToDTO(Attribute element) {
        AttributeDTO result = new AttributeDTO();
        BeanUtils.copyProperties(element, result);
        return result;
    }
    @Override
    public Attribute dtoToEntity(AttributeDTO element) {
        Attribute result = new Attribute();
        BeanUtils.copyProperties(element, result);
        return result;
    }
}
