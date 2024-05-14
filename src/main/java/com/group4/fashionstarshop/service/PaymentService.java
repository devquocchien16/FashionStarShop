package com.group4.fashionstarshop.service;

import org.springframework.stereotype.Service;

import com.group4.fashionstarshop.model.Order;
import com.group4.fashionstarshop.payload.PaymentResponse;
import com.group4.fashionstarshop.request.OrderRequest;
import com.group4.fashionstarshop.request.OrderRequestPayment;
import com.stripe.exception.StripeException;
@Service
public interface PaymentService {
	
	public PaymentResponse createPaymentLink (Order order);
	
	Order createOrderPayment(OrderRequestPayment orderRequest);
}
