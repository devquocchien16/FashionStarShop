package com.group4.fashionstarshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.group4.fashionstarshop.dto.OrderDTO;
import com.group4.fashionstarshop.exception.OrderException;
import com.group4.fashionstarshop.model.Address;
import com.group4.fashionstarshop.model.Order;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.request.OrderRequest;
import com.group4.fashionstarshop.request.UpdateOrderRequest;

@Service
public interface OrderService  {
	List<OrderDTO> findOrderByStoreId(Long storeId);	
	List<OrderDTO> findOrderByUserId(Long userId);	
	Order createOrder(OrderRequest orderRequest);
	OrderDTO findOrderByOrderId(Long orderId);
	OrderDTO updateOrderByOrderId(Long orderId, UpdateOrderRequest newStatus);
	
	
	 // try hard :v
    public List<OrderDTO> usersOrderHistory(Long userId);
    public List<OrderDTO> gettAlOrders();
    public void deleteOrder(Long orderId);
    
    public Order pendingOrder(Long orderId);
   Order acceptedOrder(Long orderId) throws OrderException;
   Order completedOrder(Long orderId) throws OrderException;
   Order canceledOrder(Long orderId) throws OrderException;
}
