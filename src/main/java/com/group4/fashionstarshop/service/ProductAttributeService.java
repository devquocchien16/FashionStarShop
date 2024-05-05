package com.group4.fashionstarshop.service;

import com.group4.fashionstarshop.dto.ProductAttributeDTO;
import com.group4.fashionstarshop.model.Attribute;
import java.util.List;

public interface ProductAttributeService {
    List<ProductAttributeDTO> getProductAttributeByProductId(Long product_id);
    List<Attribute> createProductAttribute(List<ProductAttributeDTO> productAttributeDtoList, Long product_Id);
}
