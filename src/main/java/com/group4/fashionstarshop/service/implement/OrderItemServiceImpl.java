package com.group4.fashionstarshop.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.fashionstarshop.converter.OrderItemConverter;
import com.group4.fashionstarshop.dto.OrderItemDTO;
import com.group4.fashionstarshop.model.OrderItem;
import com.group4.fashionstarshop.repository.OrderItemRepository;
import com.group4.fashionstarshop.service.OrderItemService;
 
@Service
public class OrderItemServiceImpl implements OrderItemService{

	@Autowired
	private OrderItemConverter orderItemConver;
	
	@Autowired
	private OrderItemRepository orderItemRepos;
	
	@Override
	public OrderItem createdOrderItem(OrderItemDTO orderItemDTO) {
		OrderItem orderItem = orderItemConver.dtoToEntity(orderItemDTO);
		return orderItemRepos.save(orderItem);
	}
}
