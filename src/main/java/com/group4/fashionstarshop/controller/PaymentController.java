package com.group4.fashionstarshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.group4.fashionstarshop.payload.AddressResponse;
import com.group4.fashionstarshop.payload.PaymentMethodResponse;
import com.group4.fashionstarshop.payload.ShippingMethodResponse;
import com.group4.fashionstarshop.request.AddressRequest;
import com.group4.fashionstarshop.request.PaymentMethodRequest;
import com.group4.fashionstarshop.service.AddressService;
import com.group4.fashionstarshop.service.PaymentMethodService;
import com.group4.fashionstarshop.service.ShippingMethodService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private ShippingMethodService shippingMethodService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping("/address/{userId}")
    private ResponseEntity<List<AddressResponse>> findAddress(@PathVariable("userId") Long userId){
    	System.out.println("aaaaaaaaa");
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

    @PostMapping("/address/{userId}/order")
    public ResponseEntity<AddressResponse> createAddressOrder(@PathVariable("userId") Long userId,@RequestBody AddressRequest addressRequest){
    	System.out.println(addressRequest);
    	System.out.println(userId);
        AddressResponse addressResponse = addressService.createAddressOrder(userId,addressRequest);
        return new ResponseEntity<>(addressResponse, HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/payment")
    private ResponseEntity<PaymentMethodResponse> createPaymentMethod(@PathVariable("userId") Long userId,@RequestBody PaymentMethodRequest paymentMethodRequest){
      System.out.println(paymentMethodRequest);
      System.out.println(userId);
    	PaymentMethodResponse paymentMethodResponse = paymentMethodService.createPaymentMethod(userId,paymentMethodRequest);
        return new ResponseEntity<>(paymentMethodResponse, HttpStatus.CREATED);
    }

    @PutMapping("/address")
    private ResponseEntity<AddressResponse> editAddress(@RequestBody AddressRequest addressRequest){
        AddressResponse addressResponse = addressService.updateAddressOrder(addressRequest);
        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
    }

    @PutMapping("/payment-method")
    private ResponseEntity<PaymentMethodResponse> editPaymentMethod(@RequestBody PaymentMethodRequest paymentMethodRequest){
        PaymentMethodResponse paymentMethodResponse = paymentMethodService.updatePaymentMethod(paymentMethodRequest);
        return new ResponseEntity<>(paymentMethodResponse, HttpStatus.OK);
    }
}




//
//@GetMapping("/{userId}")
//private ResponseEntity<OrderResponse> findShopOrder(@PathVariable("userId") Long userId){
//  OrderResponse orderResponse = orderService.findOrderByUserId(userId);
//  return new ResponseEntity<>(orderResponse, HttpStatus.OK);
//}

//@PostMapping
//private ResponseEntity<List<OrderResponse>> createShopOrder(@RequestBody List<OrderRequest> orderRequest){
//  List<OrderResponse> orderResponse = orderService.createOrder(orderRequest);
//  return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
//}
//