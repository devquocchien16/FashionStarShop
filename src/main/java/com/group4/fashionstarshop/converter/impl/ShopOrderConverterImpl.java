package com.group4.fashionstarshop.converter.impl;

import com.group4.fashionstarshop.converter.AddressConverter;
import com.group4.fashionstarshop.converter.PaymentMethodConverter;
import com.group4.fashionstarshop.converter.ShippingMethodConverter;
import com.group4.fashionstarshop.converter.ShopOrderConverter;
import com.group4.fashionstarshop.model.ShopOrder;
import com.group4.fashionstarshop.request.ShopOrderRequest;
import com.group4.fashionstarshop.payload.ShopOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class ShopOrderConverterImpl {
//	   @Autowired
//	    private AddressConverter addressConverter;
//
//	    @Autowired
//	    private PaymentMethodConverter paymentMethodConverter;
//
//	    @Autowired
//	    private ShippingMethodConverter shippingMethodConverter;
//
//	    @Override
//	    public ShopOrderResponse convertToDto(ShopOrder shopOrder) {
//	        return ShopOrderResponse
//	                .builder()
//	                .address(addressConverter.convertToDto(shopOrder.getAddress()))
//	                .paymentMethod(paymentMethodConverter.convertToDto(shopOrder.getPaymentMethod()))
//	                .shippingMethod(shippingMethodConverter.convertToDto(shopOrder.getShippingMethod()))
//	                .build();
//	    }
//
//	    @Override
//	    public ShopOrder convertToEntity(ShopOrderRequest shopOrderRequest) {
//	        ShopOrder shopOrder = new ShopOrder();
//	        BeanUtils.copyProperties(shopOrderRequest,shopOrder);
//	        return shopOrder;
//	    }
}
