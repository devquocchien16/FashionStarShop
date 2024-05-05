package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.ProductAttributeDTO;
import com.group4.fashionstarshop.model.Attribute;

import java.util.List;

public interface ProductAttributeConverter {
    Attribute dtoToEntity(ProductAttributeDTO element);
    List<ProductAttributeDTO> entitiesToDTOs(List<Attribute> element);
    ProductAttributeDTO entityToDTO(Attribute element);
}
