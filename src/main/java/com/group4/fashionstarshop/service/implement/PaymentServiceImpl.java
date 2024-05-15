package com.group4.fashionstarshop.service.implement;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.group4.fashionstarshop.converter.OrderConverter;
import com.group4.fashionstarshop.model.Order;
import com.group4.fashionstarshop.model.OrderItem;
import com.group4.fashionstarshop.model.ShippingMethod;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.model.Variant;
import com.group4.fashionstarshop.payload.PaymentResponse;
import com.group4.fashionstarshop.repository.AddressRepository;
import com.group4.fashionstarshop.repository.OrderItemRepository;
import com.group4.fashionstarshop.repository.OrderRepository;
import com.group4.fashionstarshop.repository.PaymentMethodRepository;
import com.group4.fashionstarshop.repository.ShippingMethodRepository;
import com.group4.fashionstarshop.repository.StoreRepository;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.repository.VariantRepository;
import com.group4.fashionstarshop.request.OrderItemRequest;
import com.group4.fashionstarshop.request.OrderRequest;
import com.group4.fashionstarshop.request.OrderRequestPayment;
import com.group4.fashionstarshop.service.EmailService;
import com.group4.fashionstarshop.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderConverter orderConverter;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private VariantRepository variantRepository;

	@Autowired
	private ShippingMethodRepository shippingMethodRepository;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private OrderItemRepository orderItemRepos;
	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

    @Override
    public PaymentResponse createPaymentLink(Order order){
        PaymentResponse res = new PaymentResponse();
        try {
            Stripe.apiKey = stripeSecretKey;
            

            // Sử dụng BigDecimal để tính toán số tiền chính xác
            BigDecimal total = new BigDecimal(String.valueOf(order.getOrderTotal()));
            long unitAmount = total.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).longValueExact();
            
            SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/success")
                .setCancelUrl("http://localhost:3000/fail")
                .addLineItem(
                    SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(unitAmount)
                                .setProductData(
                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("fashion star")
                                        .build()
                                )
                                .build()
                        )
                        .build()
                )
                .build();

            Session session = Session.create(params);
            res.setPayment_url(session.getUrl());
        } catch (StripeException e) {
            // Handle the exception (log it, modify response, etc.)
            res.setPayment_url(null);
            res.setError_message("Failed to create payment link: " + e.getMessage());
        }
        return res;
    }

   @Override
	@Transactional
	public Order createOrderPayment(OrderRequestPayment orderRequest) {
		
		Order order = new Order();
		
		User user = userRepository.findById(orderRequest.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));
		// Set user, store, order date, address, payment method, shipping method, etc.
		order.setUser(user);
		order.setStore(storeRepository.findById(orderRequest.getUserId())
				.orElseThrow(() -> new RuntimeException("Store not found")));
		order.setAddress(addressRepository.findById(orderRequest.getAddressId())
				.orElseThrow(() -> new RuntimeException("Address not found")));
		order.setPaymentMethod(paymentMethodRepository.findById((long) 11)
				.orElseThrow(() -> new RuntimeException("Payment Method not found")));
		order.setShippingMethod(shippingMethodRepository.findById(orderRequest.getShippingMethodId())
				.orElseThrow(() -> new RuntimeException("Shipping Method not found")));
		order.setOrderDate(orderRequest.getOrderDate());
	
		
		// Calculate order total based on order items
		double orderTotal = orderRequest.getOrderItemRequestList().stream()
				.mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
		
		//Find shipping price
		ShippingMethod shippingMethod = shippingMethodRepository.findById(orderRequest.getShippingMethodId()).orElseThrow(() 
				-> new RuntimeException("Shipping Method not found"));
		
		order.setOrderTotal(orderTotal + shippingMethod.getPrice());
		order.setStatus("PENDING");	
		//
		Order afterSaveOrder = orderRepository.save(order);
		// Send mail
//		emailService.sendPaymentEmail(user);

		for (OrderItemRequest orderItemReq: orderRequest.getOrderItemRequestList()) {
			OrderItem orderItem = new OrderItem();
			Variant findIdVariant = variantRepository.findVariantById(orderItemReq.getVariant_id());
			orderItem.setVariant(findIdVariant);
			orderItem.setPrice(orderItemReq.getPrice());
			orderItem.setQuantity(orderItemReq.getQuantity());
			orderItem.setShop_order(afterSaveOrder);	
			
			 orderItemRepos.save(orderItem);
		}
		// Save the newest order
		return afterSaveOrder;
	}

}
