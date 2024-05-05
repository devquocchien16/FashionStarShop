package com.group4.fashionstarshop.service;

import org.springframework.stereotype.Service;

import com.group4.fashionstarshop.dto.OrderItemDTO;
import com.group4.fashionstarshop.model.OrderItem;

@Service
public interface OrderItemService {
	
	public OrderItem createdOrderItem(OrderItemDTO orderItemDTO);
}
