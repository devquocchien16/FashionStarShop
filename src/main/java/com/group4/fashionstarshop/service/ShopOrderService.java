package com.group4.fashionstarshop.service;

import com.group4.fashionstarshop.request.ShopOrderRequest;
import com.group4.fashionstarshop.payload.ShopOrderResponse;

import java.util.List;

public interface ShopOrderService {
    ShopOrderResponse findShopOrder(Long userId);
    List<ShopOrderResponse> createShopOrder(List<ShopOrderRequest> shopOrderRequest);
}
