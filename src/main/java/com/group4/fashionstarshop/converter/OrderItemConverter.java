package com.group4.fashionstarshop.converter;

import java.util.List;

import com.group4.fashionstarshop.dto.OrderItemDTO;
import com.group4.fashionstarshop.model.OrderItem;
import com.group4.fashionstarshop.payload.OrderItemResponse;
import com.group4.fashionstarshop.request.OrderItemRequest;

public interface OrderItemConverter {
	OrderItemDTO entityToDTO(OrderItem element);

	OrderItem dtoToEntity(OrderItemDTO element);

	List<OrderItem> dtosToEntities(List<OrderItemDTO> element);

	List<OrderItemDTO> entitiesToDTOs(List<OrderItem> element);
}
