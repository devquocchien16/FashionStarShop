package com.group4.fashionstarshop.service;

import java.util.List;

import com.group4.fashionstarshop.dto.AttributeDTO;
import com.group4.fashionstarshop.request.AttributeRequest;

public interface AttributeService {
    List<AttributeDTO> geAttributeByProductId(Long product_id);
    AttributeDTO createAttribute(AttributeRequest request, Long product_Id);
	AttributeDTO updateAttribute(AttributeRequest attributeRequest, Long attribute_id);
	void deleteAttribute(Long attribute_id);
}
