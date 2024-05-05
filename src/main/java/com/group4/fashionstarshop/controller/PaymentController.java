package com.group4.fashionstarshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group4.fashionstarshop.service.OrderService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/payments")
public class PaymentController {
	 @Autowired
	    private OrderService shopOrderService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ShippingMethodService shippingMethodService;

    @Autowired
    private PaymentMethodService paymentMethodService;
//
//    @GetMapping("/{userId}")
//    private ResponseEntity<OrderResponse> findShopOrder(@PathVariable("userId") Long userId){
//        OrderResponse orderResponse = orderService.findOrderByUserId(userId);
//        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
//    }

//    @PostMapping
//    private ResponseEntity<List<OrderResponse>> createShopOrder(@RequestBody List<OrderRequest> orderRequest){
//        List<OrderResponse> orderResponse = orderService.createOrder(orderRequest);
//        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
//    }
//
    @GetMapping("/address/{userId}")
    private ResponseEntity<List<AddressResponse>> findAddress(@PathVariable("userId") Long userId){
        List<AddressResponse> addressResponse = addressService.findAddress(userId);
        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
    }

    @GetMapping("/shipping-method")
    private ResponseEntity<List<ShippingMethodResponse>> findShippingMethod(){
        List<ShippingMethodResponse> shippingMethodResponseList = shippingMethodService.findShippingMethod();
        return new ResponseEntity<>(shippingMethodResponseList, HttpStatus.OK);
    }

    @GetMapping("/payment-method/{userId}")
    private ResponseEntity<List<PaymentMethodResponse>> findPaymentMethod(@PathVariable("userId") Long userId){
        List<PaymentMethodResponse> paymentMethodResponseList = paymentMethodService.findPaymentMethod(userId);
        return new ResponseEntity<>(paymentMethodResponseList, HttpStatus.OK);
    }

    @PostMapping("/address")
    private ResponseEntity<AddressResponse> createAddress(@RequestBody AddressRequest addressRequest){
        AddressResponse addressResponse = addressService.createAddress(addressRequest);
        return new ResponseEntity<>(addressResponse, HttpStatus.CREATED);
    }

    @PostMapping("/payment-method")
    private ResponseEntity<PaymentMethodResponse> createPaymentMethod(@RequestBody PaymentMethodRequest paymentMethodRequest){
        PaymentMethodResponse paymentMethodResponse = paymentMethodService.createPaymentMethod(paymentMethodRequest);
        return new ResponseEntity<>(paymentMethodResponse, HttpStatus.CREATED);
    }

    @PutMapping("/address")
    private ResponseEntity<AddressResponse> editAddress(@RequestBody AddressRequest addressRequest){
        AddressResponse addressResponse = addressService.updateAddress(addressRequest);
        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
    }

    @PutMapping("/payment-method")
    private ResponseEntity<PaymentMethodResponse> editPaymentMethod(@RequestBody PaymentMethodRequest paymentMethodRequest){
        PaymentMethodResponse paymentMethodResponse = paymentMethodService.updatePaymentMethod(paymentMethodRequest);
        return new ResponseEntity<>(paymentMethodResponse, HttpStatus.OK);
    }
}
