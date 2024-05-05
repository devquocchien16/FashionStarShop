package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.model.ShopOrder;
import com.group4.fashionstarshop.request.ShopOrderRequest;
import com.group4.fashionstarshop.payload.ShopOrderResponse;

public interface ShopOrderConverter {
    ShopOrderResponse convertToDto(ShopOrder shopOrder);
    ShopOrder convertToEntity(ShopOrderRequest shopOrderRequest);
}
