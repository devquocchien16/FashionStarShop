package com.group4.fashionstarshop.sellercontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group4.fashionstarshop.dto.OrderDTO;
import com.group4.fashionstarshop.request.UpdateOrderRequest;
import com.group4.fashionstarshop.service.OrderService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/seller/order")
public class OrderManageController {
    @Autowired
    private OrderService orderService;
    
    @GetMapping("/{storeId}/all")
    public List<OrderDTO> getOrderByStoreId(@PathVariable("storeId") Long storeId) {    	
    	return orderService.findOrderByStoreId(storeId);
    }
    
    @PostMapping("/update/{orderId}")
    public OrderDTO getOrderByStoreId(@RequestBody UpdateOrderRequest request,     		
    		@PathVariable("orderId") Long orderId) {    	
    	return orderService.updateOrderByOrderId(orderId, request);
    }
  


}
