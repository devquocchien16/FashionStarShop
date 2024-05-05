package com.group4.fashionstarshop.dto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopOrderDTO {
    private Long id;
    private UserDTO userDTO;
    private List<VariantDTO> variantDtoList;
    private String order_date;
    private AddressDTO addressDto;
    private String message;
    private PaymentMethodDTO paymentMethodDto;
    private ShippingMethodDTO shippingMethodDto;
    private double orderTotal;
}
