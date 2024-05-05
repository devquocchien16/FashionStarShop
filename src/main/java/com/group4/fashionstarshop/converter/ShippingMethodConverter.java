package com.group4.fashionstarshop.converter;

import java.util.List;

import com.group4.fashionstarshop.dto.ShippingMethodDTO;
import com.group4.fashionstarshop.model.ShippingMethod;
import com.group4.fashionstarshop.payload.ShippingMethodResponse;

public interface ShippingMethodConverter {
	ShippingMethodDTO entityToDTO(ShippingMethod element);

	ShippingMethod dtoToEntity(ShippingMethodDTO element);

	List<ShippingMethod> dtosToEntities(List<ShippingMethodDTO> element);

	List<ShippingMethodDTO> entitiesToDTOs(List<ShippingMethod> element);

	ShippingMethodResponse convertToDto(ShippingMethod shippingMethod);
}
