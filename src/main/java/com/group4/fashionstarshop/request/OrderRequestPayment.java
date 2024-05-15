package com.group4.fashionstarshop.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestPayment {
	   private Long userId;  
	    private String orderDate;
	    private Long addressId;
	    private Long shippingMethodId;   
	    private Long paymentMethodId;

	    private Double orderTotal;
	    private List<OrderItemRequest> orderItemRequestList;
}
