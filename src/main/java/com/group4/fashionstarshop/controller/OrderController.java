package com.group4.fashionstarshop.controller;

import com.group4.fashionstarshop.converter.OrderConverter;
import com.group4.fashionstarshop.converter.SellerConverter;
import com.group4.fashionstarshop.dto.OrderDTO;
import com.group4.fashionstarshop.dto.SellerDTO;
import com.group4.fashionstarshop.model.Address;
import com.group4.fashionstarshop.model.Order;
import com.group4.fashionstarshop.model.OrderItem;
import com.group4.fashionstarshop.model.PaymentMethod;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.ShippingMethod;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.model.Variant;
import com.group4.fashionstarshop.payload.AddressResponse;
import com.group4.fashionstarshop.payload.PaymentMethodResponse;
import com.group4.fashionstarshop.payload.PaymentResponse;
import com.group4.fashionstarshop.repository.AddressRepository;
import com.group4.fashionstarshop.repository.OrderItemRepository;
import com.group4.fashionstarshop.repository.OrderRepository;
import com.group4.fashionstarshop.repository.PaymentMethodRepository;
import com.group4.fashionstarshop.repository.SellerRepository;
import com.group4.fashionstarshop.repository.ShippingMethodRepository;
import com.group4.fashionstarshop.repository.StoreRepository;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.repository.VariantRepository;
import com.group4.fashionstarshop.request.AddressRequest;
import com.group4.fashionstarshop.request.OrderItemRequest;
import com.group4.fashionstarshop.request.OrderRequest;
import com.group4.fashionstarshop.request.OrderRequestPayment;
import com.group4.fashionstarshop.service.AddressService;
import com.group4.fashionstarshop.service.EmailService;
import com.group4.fashionstarshop.service.OrderService;
import com.group4.fashionstarshop.service.PaymentMethodService;
import com.group4.fashionstarshop.service.PaymentService;
import com.group4.fashionstarshop.service.UserService;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import com.group4.fashionstarshop.dto.CategoryDTO;
import com.group4.fashionstarshop.dto.OrderDTO;
import com.group4.fashionstarshop.dto.SellerDTO;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.repository.OrderRepository;
import com.group4.fashionstarshop.repository.SellerRepository;
import com.group4.fashionstarshop.service.OrderService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/orders")
public class OrderController {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@Autowired
	private PaymentService paymentService;

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
	private ShippingMethodRepository shippingMethodRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private OrderItemRepository orderItemRepos;
	@Autowired
	private PaymentMethodRepository paymentMethodRepository;
	@Autowired
	private AddressService addressService;
	@Autowired
	private PaymentMethodService paymentMethodService;
	@Autowired
	private ModelMapper mapper;

	@PostMapping("/create1")
	public ResponseEntity<String> createOrder1(@RequestBody OrderRequest orderRequest) {
		Order order = orderService.createOrder(orderRequest);
		System.out.println("order" + order);
		return new ResponseEntity<>("col", HttpStatus.CREATED);
	}

	@PostMapping("/createpayment")
	public ResponseEntity<PaymentResponse> createOrderPayment(@RequestBody OrderRequestPayment orderRequestPm) {
		Order order = paymentService.createOrderPayment(orderRequestPm);

		PaymentResponse res = paymentService.createPaymentLink(order);
		System.out.println("order" + order);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping("/saveOrderCatFlutterProVip/")
	public ResponseEntity<?> saveOrderCatFlutterProVip(@RequestBody FlutterOrderDTO dto) {
		Order order = new Order();
		System.out.print(dto);
		User user = userRepository.findById(Long.parseLong(dto.userId))
				.orElseThrow(() -> new RuntimeException("User not found"));
		order.setUser(user);
		order.setStore(storeRepository.findById(Long.parseLong(dto.userId))
				.orElseThrow(() -> new RuntimeException("Store not found")));
		order.setAddress(addressRepository.findById(Long.parseLong(dto.addressId))
				.orElseThrow(() -> new RuntimeException("Address not found")));
		order.setShippingMethod(shippingMethodRepository.findById((long) 1)
				.orElseThrow(() -> new RuntimeException("Shipping Method not found")));
		
		order.setPaymentMethod(paymentMethodRepository.findById(Long.parseLong(dto.paymentMethodId))
				.orElseThrow(() -> new RuntimeException("Shipping Method not found")));
	
		order.setOrderDate(dto.orderDate);
		order.setOrderTotal((Double.valueOf(dto.orderTotal) + 8));
		order.setStatus("PENDING");
		//
		var result=orderRepository.save(order);
		System.out.println("order" + orderRepository.save(order));
		return new ResponseEntity<>(result.getId(),HttpStatus.OK);
	}

	@PostMapping("/saveOrderItemCatFlutterProVip/")
	public ResponseEntity<?> saveOrderItemCatFlutterProVip(@RequestBody FlutterOrderItem dto) {
		OrderItem orderItem = new OrderItem();
		Variant findIdVariant = variantRepository.findVariantById(Long.parseLong(dto.variantId));
		orderItem.setVariant(findIdVariant);
		orderItem.setPrice(Double.valueOf(dto.price));
		orderItem.setQuantity(Integer.parseInt(dto.quantity));
		orderItem.setShop_order(orderRepository.findById(Long.parseLong(dto.orderId)).get());
		orderItemRepos.save(orderItem);
		System.out.println("order" + orderItem);
		return new ResponseEntity<>(orderItem, HttpStatus.OK);
	}

	@PostMapping("/saveAddressCatFlutterProVip/")
	public ResponseEntity<?> saveAddressCatFlutterProVip(@RequestBody FlutterAddress dto) {

		AddressRequest address = new AddressRequest();
		address.setCity(dto.city);
		address.setDistrict(dto.district);
		address.setStreet(dto.street);
		addressService.createAddressOrder(Long.parseLong(dto.userId), address);
		return new ResponseEntity<>(address, HttpStatus.CREATED);
	}

	@GetMapping("/getPaymentMethodCatFlutterProVip/")
	public ResponseEntity<?> findPaymentMethod() {
		List<PaymentMethod> paymentMethodResponseList = paymentMethodService.findAll();
		List<PaymentMethodResponse> result = new ArrayList<>();
		for (var item : paymentMethodResponseList) {
			result.add(mapper.map(item, PaymentMethodResponse.class));
		}
		System.out.print(result.size());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<OrderDTO>> userOrderHistory(@PathVariable("userId") Long userId) {
		User user = userService.findById(userId);
		List<OrderDTO> orders = orderService.usersOrderHistory(userId);
		return new ResponseEntity<List<OrderDTO>>(orders, HttpStatus.OK);
	}

	@GetMapping("/{orderId}/{userId}")
	public ResponseEntity<OrderDTO> findOrderById(@PathVariable("orderId") Long orderId,
			@PathVariable("userId") Long userId) {
		User user = userService.findById(userId);
		OrderDTO order = orderService.findOrderByOrderId(orderId);
		return new ResponseEntity<OrderDTO>(order, HttpStatus.OK);
	}

}
