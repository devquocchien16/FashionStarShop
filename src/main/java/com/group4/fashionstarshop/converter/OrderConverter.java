package com.group4.fashionstarshop.converter;

import java.util.List;

import com.group4.fashionstarshop.dto.OptionValueDTO;
import com.group4.fashionstarshop.dto.OrderDTO;
import com.group4.fashionstarshop.model.OptionValue;
import com.group4.fashionstarshop.model.Order;
import com.group4.fashionstarshop.request.OrderRequest;
import com.group4.fashionstarshop.payload.OrderResponse;

public interface OrderConverter {
	OrderDTO entityToDTO(Order element);

	Order dtoToEntity(OrderDTO element);

	List<Order> dtosToEntities(List<OrderDTO> element);

	List<OrderDTO> entitiesToDTOs(List<Order> element);
}
