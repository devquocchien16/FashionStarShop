package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.AttributeDTO;
import com.group4.fashionstarshop.model.Attribute;

import java.util.List;

public interface AttributeConverter {
    Attribute dtoToEntity(AttributeDTO element);
    List<AttributeDTO> entitiesToDTOs(List<Attribute> element);
    AttributeDTO entityToDTO(Attribute element);
}
