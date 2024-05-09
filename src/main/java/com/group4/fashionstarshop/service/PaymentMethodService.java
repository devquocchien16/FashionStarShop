package com.group4.fashionstarshop.service;

import com.group4.fashionstarshop.model.PaymentMethod;
import com.group4.fashionstarshop.request.PaymentMethodRequest;
import com.group4.fashionstarshop.payload.PaymentMethodResponse;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethodResponse> findPaymentMethod(Long userId);
    PaymentMethodResponse createPaymentMethod(Long userId,PaymentMethodRequest paymentMethodRequest);
    PaymentMethodResponse updatePaymentMethod(PaymentMethodRequest paymentMethodRequest);
}
