package com.group4.fashionstarshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private UserDTO userDTO;    
    private StoreDTO storeDTO;    
    private List<OrderItemDTO> orderItemListDTO;
    private String order_date;   
    private String order_status;  
    private AddressDTO addressDTO;   
    private PaymentMethodDTO paymentMethodDTO;
    private ShippingMethodDTO shippingMethodDTO;
    private double orderTotal;
}
