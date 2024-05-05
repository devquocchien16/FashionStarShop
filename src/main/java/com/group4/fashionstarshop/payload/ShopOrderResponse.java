package com.group4.fashionstarshop.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopOrderResponse {

    AddressResponse address;

    PaymentMethodResponse paymentMethod;

    ShippingMethodResponse shippingMethod;
}