package com.group4.fashionstarshop.service.implement;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group4.fashionstarshop.converter.OrderConverter;
import com.group4.fashionstarshop.dto.AddressDTO;
import com.group4.fashionstarshop.dto.OrderDTO;
import com.group4.fashionstarshop.dto.OrderItemDTO;
import com.group4.fashionstarshop.dto.PaymentMethodDTO;
import com.group4.fashionstarshop.dto.ShippingMethodDTO;
import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.dto.UserDTO;
import com.group4.fashionstarshop.exception.OrderException;
import com.group4.fashionstarshop.model.Address;
import com.group4.fashionstarshop.model.Order;
import com.group4.fashionstarshop.model.Variant;
import com.group4.fashionstarshop.model.OrderItem;
import com.group4.fashionstarshop.model.PaymentMethod;
import com.group4.fashionstarshop.model.ShippingMethod;
import com.group4.fashionstarshop.model.Store;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.repository.AddressRepository;
import com.group4.fashionstarshop.repository.OrderItemRepository;
import com.group4.fashionstarshop.repository.OrderRepository;
import com.group4.fashionstarshop.repository.PaymentMethodRepository;
import com.group4.fashionstarshop.repository.ShippingMethodRepository;
import com.group4.fashionstarshop.repository.StoreRepository;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.repository.VariantRepository;
import com.group4.fashionstarshop.request.OrderItemRequest;
import com.group4.fashionstarshop.request.OrderRequest;
import com.group4.fashionstarshop.request.ShopOrderRequest;
import com.group4.fashionstarshop.request.UpdateOrderRequest;
import com.group4.fashionstarshop.service.EmailService;
import com.group4.fashionstarshop.service.OrderService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderConverter orderConverter;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private VariantRepository variantRepository;

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Autowired
	private ShippingMethodRepository shippingMethodRepository;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private OrderItemRepository orderItemRepos;

	@Override
	@Transactional
	public Order createOrder(OrderRequest orderRequest) {
		
		Order order = new Order();
		
		User user = userRepository.findById(orderRequest.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));
		// Set user, store, order date, address, payment method, shipping method, etc.
		order.setUser(user);
		order.setStore(storeRepository.findById(orderRequest.getUserId())
				.orElseThrow(() -> new RuntimeException("Store not found")));
		order.setAddress(addressRepository.findById(orderRequest.getAddressId())
				.orElseThrow(() -> new RuntimeException("Address not found")));
		order.setPaymentMethod(paymentMethodRepository.findById(orderRequest.getPaymentMethodId())
				.orElseThrow(() -> new RuntimeException("Payment Method not found")));
		order.setShippingMethod(shippingMethodRepository.findById(orderRequest.getShippingMethodId())
				.orElseThrow(() -> new RuntimeException("Shipping Method not found")));
		order.setOrderDate(orderRequest.getOrderDate());
	
		
		// Calculate order total based on order items
		double orderTotal = orderRequest.getOrderItemRequestList().stream()
				.mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
		
		//Find shipping price
		ShippingMethod shippingMethod = shippingMethodRepository.findById(orderRequest.getShippingMethodId()).orElseThrow(() -> new RuntimeException("Shipping Method not found"));
		
		order.setOrderTotal(orderTotal + shippingMethod.getPrice());
		order.setStatus("PENDING");	
		//
		Order afterSaveOrder = orderRepository.save(order);
		// Send mail
//		emailService.sendPaymentEmail(user);

		for (OrderItemRequest orderItemReq: orderRequest.getOrderItemRequestList()) {
			OrderItem orderItem = new OrderItem();
			Variant findIdVariant = variantRepository.findVariantById(orderItemReq.getVariant_id());
			orderItem.setVariant(findIdVariant);
			orderItem.setPrice(orderItemReq.getPrice());
			orderItem.setQuantity(orderItemReq.getQuantity());
			orderItem.setShop_order(afterSaveOrder);	
			
			 orderItemRepos.save(orderItem);
		}
		// Save the newest order
		return afterSaveOrder;
	}

	@Override
	public List<OrderDTO> findOrderByStoreId(Long storeId) {
		List<Order> orderListByStoreId = orderRepository.findByStoreId(storeId);
		return orderConverter.entitiesToDTOs(orderListByStoreId);
	}

	@Override
	public OrderDTO findOrderByOrderId(Long orderId) {
		Order orderByOrderId = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found"));
		
		return orderConverter.entityToDTO(orderByOrderId);
	}

	@Override
	public List<OrderDTO> findOrderByUserId(Long userId) {
		List<Order> orderListByUserId = orderRepository.findByUserId(userId);
		return orderConverter.entitiesToDTOs(orderListByUserId);
	}

	@Override
	public List<OrderDTO> usersOrderHistory(Long userId) {
		List<Order> orders = orderRepository.getUserOder(userId);
		return orderConverter.entitiesToDTOs(orders);
	}

	@Override
	public List<OrderDTO> gettAlOrders() {
		List<Order> orderList = orderRepository.findAll();
		return orderConverter.entitiesToDTOs(orderList);
	}

	@Override
	public void deleteOrder(Long orderId) {
		findOrderByOrderId(orderId);
		orderRepository.deleteById(orderId);
	}	
	
	@Override
	public Order pendingOrder(Long orderId) {
		OrderDTO orderDTO = findOrderByOrderId(orderId);
		orderDTO.setOrder_status("PENDING");		
		Order order =  orderConverter.dtoToEntity(orderDTO);
				return orderRepository.save(order);
	}
	@Override
	public Order acceptedOrder(Long orderId) throws OrderException {
		OrderDTO orderDTO = findOrderByOrderId(orderId);
		orderDTO.setOrder_status("ACCEPTED");
		Order order = orderConverter.dtoToEntity(orderDTO);
		return orderRepository.save(order);
	}

	@Override
	public Order completedOrder(Long orderId) throws OrderException {
		OrderDTO orderDTO = findOrderByOrderId(orderId);
		orderDTO.setOrder_status("COMPLETED");
		Order order = orderConverter.dtoToEntity(orderDTO);		
		return orderRepository.save(order);
	}

	@Override
	public Order canceledOrder(Long orderId) throws OrderException {
		OrderDTO orderDTO = findOrderByOrderId(orderId);
		orderDTO.setOrder_status("CANCEL");
		Order order = orderConverter.dtoToEntity(orderDTO);		
		return orderRepository.save(order);
	}

	@Override
	public OrderDTO processAcceptOrder(Long order_id) {
		Order orderByOrderId = orderRepository.findById(order_id)
				.orElseThrow(() -> new RuntimeException("Order not found"));		
		orderByOrderId.setStatus("ACCEPTED");		
			List<OrderItem> orderItemList = orderByOrderId.getOrderItemList();
			for (OrderItem orderItem : orderItemList) {
				int orderQuantity = orderItem.getQuantity();
				int quantityInStock = orderItem.getVariant().getStockQuantity();
				// Assuming itemId và quantityToDecrease đã được định nghĩa trước đó
				if (orderQuantity <= quantityInStock) {
					quantityInStock = quantityInStock - orderQuantity;
					Variant variant = variantRepository.findById(orderItem.getVariant().getId())
							.orElseThrow(() -> new RuntimeException("Variant not found"));
					variant.setStockQuantity(quantityInStock);
				} else {
					throw new RuntimeException("Not enough quantity to decrease for item: " + orderItem.getId());
				}
			}	

		orderByOrderId.setAcceptedAt(new Date());
		orderRepository.save(orderByOrderId);
		return orderConverter.entityToDTO(orderByOrderId);
	}

	@Override
	public OrderDTO processDeliverOrder(Long order_id) {
		Order orderByOrderId = orderRepository.findById(order_id)
				.orElseThrow(() -> new RuntimeException("Order not found"));		
		orderByOrderId.setStatus("DELIVERING");	
		orderByOrderId.setDeliveringAt(new Date());
		orderRepository.save(orderByOrderId);
		return orderConverter.entityToDTO(orderByOrderId);
	}

	@Override
	public OrderDTO processCancelOrder(Long order_id) {
		Order orderByOrderId = orderRepository.findById(order_id)
				.orElseThrow(() -> new RuntimeException("Order not found"));		
		orderByOrderId.setStatus("CANCEL");	
		orderByOrderId.setCanceledAt(new Date());
		orderRepository.save(orderByOrderId);
		return orderConverter.entityToDTO(orderByOrderId);
	}
	
	@Override
	public OrderDTO processCompleteOrder(Long order_id) {
		Order orderByOrderId = orderRepository.findById(order_id)
				.orElseThrow(() -> new RuntimeException("Order not found"));		
		orderByOrderId.setStatus("COMPLETED");	
		orderByOrderId.setCompletedAt(new Date());
		orderRepository.save(orderByOrderId);
		return orderConverter.entityToDTO(orderByOrderId);
	}

}
