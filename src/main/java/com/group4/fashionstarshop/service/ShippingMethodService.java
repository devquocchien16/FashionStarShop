package com.group4.fashionstarshop.service;

import com.group4.fashionstarshop.payload.ShippingMethodResponse;

import java.util.List;

public interface ShippingMethodService {
    List<ShippingMethodResponse> findShippingMethod();
}
