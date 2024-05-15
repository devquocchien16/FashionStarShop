package com.group4.fashionstarshop.request;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {   
    private Long userId;  
    private Date orderDate;
    private Long addressId;
    private Long paymentMethodId;
    private Long shippingMethodId;   
    private Double orderTotal;
    private List<OrderItemRequest> orderItemRequestList;
}
