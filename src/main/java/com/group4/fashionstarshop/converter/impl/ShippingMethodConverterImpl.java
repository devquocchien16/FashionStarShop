package com.group4.fashionstarshop.converter.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.group4.fashionstarshop.converter.ShippingMethodConverter;
import com.group4.fashionstarshop.dto.ShippingMethodDTO;
import com.group4.fashionstarshop.model.ShippingMethod;
import com.group4.fashionstarshop.payload.ShippingMethodResponse;

@Component
public class ShippingMethodConverterImpl implements ShippingMethodConverter {
	@Override
	public ShippingMethodDTO entityToDTO(ShippingMethod element) {
		ShippingMethodDTO result = new ShippingMethodDTO();
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public ShippingMethod dtoToEntity(ShippingMethodDTO element) {
		ShippingMethod result = new ShippingMethod();
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public List<ShippingMethod> dtosToEntities(List<ShippingMethodDTO> element) {
		return element.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}

	@Override
	public List<ShippingMethodDTO> entitiesToDTOs(List<ShippingMethod> element) {
		return element.stream().map(this::entityToDTO).collect(Collectors.toList());
	}

	  @Override
	    public ShippingMethodResponse convertToDto(ShippingMethod shippingMethod) {
	        return ShippingMethodResponse
	                .builder()
	                .id(shippingMethod.getId())
	                .name(shippingMethod.getName())
	                .price(shippingMethod.getPrice())
	                .build();
	    }
}
