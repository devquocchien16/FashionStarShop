package com.group4.fashionstarshop.converter.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.group4.fashionstarshop.converter.OrderItemConverter;
import com.group4.fashionstarshop.converter.StoreConverter;
import com.group4.fashionstarshop.converter.VariantConverter;
import com.group4.fashionstarshop.dto.OrderItemDTO;
import com.group4.fashionstarshop.model.OrderItem;

@Component
public class OrderItemConverterImpl implements OrderItemConverter {

	@Autowired
	private VariantConverter variantConverter;
	
	@Override
	public OrderItemDTO entityToDTO(OrderItem element) {
		OrderItemDTO result = new OrderItemDTO();
		result.setVariantDTO(variantConverter.entityToDTO(element.getVariant()));
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public OrderItem dtoToEntity(OrderItemDTO element) {
		OrderItem result = new OrderItem();
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public List<OrderItem> dtosToEntities(List<OrderItemDTO> element) {
		return element.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}

	@Override
	public List<OrderItemDTO> entitiesToDTOs(List<OrderItem> element) {
		return element.stream().map(this::entityToDTO).collect(Collectors.toList());
	}

}
