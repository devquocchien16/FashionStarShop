package com.group4.fashionstarshop.controller;

import com.group4.fashionstarshop.converter.SellerConverter;
import com.group4.fashionstarshop.dto.OrderDTO;
import com.group4.fashionstarshop.dto.SellerDTO;
import com.group4.fashionstarshop.model.Address;
import com.group4.fashionstarshop.model.Order;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.repository.OrderRepository;
import com.group4.fashionstarshop.repository.SellerRepository;
import com.group4.fashionstarshop.request.OrderRequest;
import com.group4.fashionstarshop.service.OrderService;
import com.group4.fashionstarshop.service.UserService;

import java.util.List;
import com.group4.fashionstarshop.dto.CategoryDTO;
import com.group4.fashionstarshop.dto.OrderDTO;
import com.group4.fashionstarshop.dto.SellerDTO;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.repository.OrderRepository;
import com.group4.fashionstarshop.repository.SellerRepository;
import com.group4.fashionstarshop.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    @PostMapping("/create1")
    public ResponseEntity<String> createOrder1(@RequestBody OrderRequest orderRequest){
    	 Order order = orderService.createOrder(orderRequest);
    	 System.out.println("order" + order);
    	return  new ResponseEntity<>("col", HttpStatus.CREATED);
    }
  
    	
   	@GetMapping("/{userId}")
   	public ResponseEntity<List<OrderDTO>> userOrderHistory(@PathVariable("userId") Long userId){
   		User user = userService.findById(userId);
   		List<OrderDTO> orders = orderService.usersOrderHistory(userId);
   		return new ResponseEntity<List<OrderDTO>>(orders, HttpStatus.OK);
   	}
   	
   	
   	@GetMapping("/{orderId}/{userId}")
   	public ResponseEntity<OrderDTO> findOrderById(@PathVariable("orderId") Long orderId, @PathVariable("userId") Long userId){
   		User user = userService.findById(userId);
   		OrderDTO order = orderService.findOrderByOrderId(orderId);
   		return new ResponseEntity<OrderDTO>(order, HttpStatus.OK);   		
   	}
   	
 	
   	
}
