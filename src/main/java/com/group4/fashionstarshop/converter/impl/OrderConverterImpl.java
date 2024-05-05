package com.group4.fashionstarshop.converter.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.group4.fashionstarshop.converter.AddressConverter;
import com.group4.fashionstarshop.converter.OrderConverter;
import com.group4.fashionstarshop.converter.OrderItemConverter;
import com.group4.fashionstarshop.converter.PaymentMethodConverter;
import com.group4.fashionstarshop.converter.ShippingMethodConverter;
import com.group4.fashionstarshop.converter.StoreConverter;
import com.group4.fashionstarshop.converter.UserConverter;
import com.group4.fashionstarshop.dto.OrderDTO;
import com.group4.fashionstarshop.model.Order;

@Component
public class OrderConverterImpl implements OrderConverter {
	@Autowired
	private UserConverter userConverter;
	@Autowired
	private AddressConverter addressConverter;
	@Autowired
	private PaymentMethodConverter paymentMethodConverter;
	@Autowired
	private ShippingMethodConverter shippingMethodConverter;
	@Autowired
	private OrderItemConverter orderItemConverter;
	
	@Autowired
	private StoreConverter storeConverter;

	@Override
	public OrderDTO entityToDTO(Order element) {
		OrderDTO result = new OrderDTO();
	
		result.setUserDTO(userConverter.convertEntityToDTO(element.getUser()));
		result.setStoreDTO(storeConverter.entityToDTO(element.getStore()));		
		result.setAddressDTO(addressConverter.entityToDTO(element.getAddress()));
		result.setPaymentMethodDTO(paymentMethodConverter.entityToDTO(element.getPaymentMethod()));
		result.setShippingMethodDTO(shippingMethodConverter.entityToDTO(element.getShippingMethod()));
		result.setOrderItemListDTO(orderItemConverter.entitiesToDTOs(element.getOrderItemList()));
		result.setOrder_date(element.getOrderDate());
		result.setOrder_status(element.getStatus());		
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public Order dtoToEntity(OrderDTO element) {
		Order result = new Order();
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public List<Order> dtosToEntities(List<OrderDTO> element) {
		return element.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}

	@Override
	public List<OrderDTO> entitiesToDTOs(List<Order> element) {
		return element.stream().map(this::entityToDTO).collect(Collectors.toList());
	}
}
