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
@RequestMapping("api/seller/order")
public class OrderManageController {
    @Autowired
    private OrderService orderService;
    
    @GetMapping("/{storeId}/all")
    public List<OrderDTO> getOrderByStoreId(@PathVariable("storeId") Long storeId) {    	
    	return orderService.findOrderByStoreId(storeId);
    }
    @GetMapping("/details/{order_id}")
    public OrderDTO getOrderById(@PathVariable("order_id") Long order_id) {    	
    	return orderService.findOrderByOrderId(order_id);
    }
    
    @PostMapping("/accept/{order_id}")
    public OrderDTO acceptOrder(@PathVariable("order_id") Long order_id) {    	
    	return orderService.processAcceptOrder(order_id);
    }
    @PostMapping("/deliver/{order_id}")
    public OrderDTO deliveringOrder(@PathVariable("order_id") Long order_id) {    	
    	return orderService.processDeliverOrder(order_id);
    }
    @PostMapping("/cancel/{order_id}")
    public OrderDTO cancelOrder(@PathVariable("order_id") Long order_id) {    	
    	return orderService.processCancelOrder(order_id);
    }
    @PostMapping("/complete/{order_id}")
    public OrderDTO completeOrder(@PathVariable("order_id") Long order_id) {    	
    	return orderService.processCompleteOrder(order_id);
    }
}
