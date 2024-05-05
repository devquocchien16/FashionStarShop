package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.converter.ShopOrderConverter;
import com.group4.fashionstarshop.model.Address;
import com.group4.fashionstarshop.model.PaymentMethod;
import com.group4.fashionstarshop.model.ShippingMethod;
import com.group4.fashionstarshop.model.ShopOrder;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.model.Variant;
import com.group4.fashionstarshop.request.ShopOrderRequest;
import com.group4.fashionstarshop.payload.ShopOrderResponse;
import com.group4.fashionstarshop.repository.AddressRepository;
import com.group4.fashionstarshop.repository.PaymentMethodRepository;
import com.group4.fashionstarshop.repository.ShippingMethodRepository;
import com.group4.fashionstarshop.repository.ShopOrderRepository;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.repository.VariantRepository;
import com.group4.fashionstarshop.service.EmailService;
import com.group4.fashionstarshop.service.ShopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ShopOrderServiceImpl implements ShopOrderService {

    @Autowired
    private ShopOrderRepository shopOrderRepository;

    @Autowired
    private ShopOrderConverter shopOrderConverter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private ShippingMethodRepository shippingMethodRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public ShopOrderResponse findShopOrder(Long userId) {
        ShopOrder shopOrder = shopOrderRepository.findByUserId(userId);
        return shopOrderConverter.convertToDto(shopOrder);
    }

    @Override
    public List<ShopOrderResponse> createShopOrder(List<ShopOrderRequest> shopOrderRequest) {
        List<ShopOrderResponse> shopOrderResponseList = new ArrayList<>();
        User user = new User();
        for(ShopOrderRequest shopOrder: shopOrderRequest){
            ShopOrder shopOrderOld = shopOrderConverter.convertToEntity(shopOrder);
            User takeUser = userRepository.findById(shopOrder.getUserId()).get();
            user = takeUser;
            Variant variant = variantRepository.findById(shopOrder.getVariantId()).get();
            Address address = addressRepository.findById(shopOrder.getAddressId()).get();
            PaymentMethod paymentMethod = paymentMethodRepository.findById(shopOrder.getPaymentMethodId()).get();
            ShippingMethod shippingMethod = shippingMethodRepository.findById(shopOrder.getShippingMethodId()).get();
            shopOrderOld.setUser(takeUser);
            shopOrderOld.setVariant(variant);
            shopOrderOld.setAddress(address);
            shopOrderOld.setPaymentMethod(paymentMethod);
            shopOrderOld.setShippingMethod(shippingMethod);
            ShopOrder shopOrderNew = shopOrderRepository.save(shopOrderOld);
            ShopOrderResponse shopOrderResponse = shopOrderConverter.convertToDto(shopOrderNew);
            shopOrderResponseList.add(shopOrderResponse);
        }
        emailService.sendPaymentEmail(user);
        return shopOrderResponseList;
    }
}
