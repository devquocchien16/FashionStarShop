package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.converter.PaymentMethodConverter;
import com.group4.fashionstarshop.model.PaymentMethod;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.request.PaymentMethodRequest;
import com.group4.fashionstarshop.payload.PaymentMethodResponse;
import com.group4.fashionstarshop.repository.PaymentMethodRepository;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private final PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private final PaymentMethodConverter paymentMethodConverter;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private  ModelMapper modelMapper;

    @Override
    public List<PaymentMethodResponse> findPaymentMethod(Long userId) {
        List<PaymentMethod> paymentMethodList = paymentMethodRepository.findByUserId(userId);
        List<PaymentMethodResponse> paymentMethodResponseList = new ArrayList<>();
        for(PaymentMethod paymentMethod : paymentMethodList){
            PaymentMethodResponse paymentMethodResponse = paymentMethodConverter.convertToDto(paymentMethod);
            paymentMethodResponseList.add(paymentMethodResponse);
        }
        return paymentMethodResponseList;
    }

    @Override
    public PaymentMethodResponse createPaymentMethod(Long userId,PaymentMethodRequest paymentMethodRequest) {
        User user = userRepository.findById(userId) .orElseThrow();
        
    	PaymentMethod paymentMethod = modelMapper.map(paymentMethodRequest, PaymentMethod.class);

    	paymentMethod.setUser(user);
        PaymentMethod paymentMethodNew = paymentMethodRepository.save(paymentMethod);
        return modelMapper.map(paymentMethodNew, PaymentMethodResponse.class);
    }

    @Override
    public PaymentMethodResponse updatePaymentMethod(PaymentMethodRequest paymentMethodRequest) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodRequest.getId()).get();
        paymentMethod.setCartNumber(paymentMethodRequest.getCartNumber());
        paymentMethod.setNameOnCard(paymentMethodRequest.getNameOnCard());
        paymentMethod.setExpirationDate(paymentMethodRequest.getExpirationDate());
        paymentMethod.setDefaultPayment(paymentMethodRequest.getDefaultPayment());
        PaymentMethod paymentMethodNew = paymentMethodRepository.save(paymentMethod);
        return paymentMethodConverter.convertToDto(paymentMethodNew);
    }
}
