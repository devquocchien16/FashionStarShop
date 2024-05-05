package com.group4.fashionstarshop.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.group4.fashionstarshop.converter.ShippingMethodConverter;
import com.group4.fashionstarshop.model.ShippingMethod;
import com.group4.fashionstarshop.dto.ShippingMethodDTO;
import com.group4.fashionstarshop.payload.ShippingMethodResponse;
import com.group4.fashionstarshop.repository.ShippingMethodRepository;
import com.group4.fashionstarshop.service.ShippingMethodService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ShippingMethodServiceImpl implements ShippingMethodService {
    @Autowired
    private final ShippingMethodRepository shippingMethodRepository;

    @Qualifier("shippingMethodConverterImpl")
    @Autowired
    private final ShippingMethodConverter shippingMethodConverter;

    @Override
    public List<ShippingMethodResponse> findShippingMethod() {
        List<ShippingMethod> shippingMethodList = shippingMethodRepository.findAll();
        List<ShippingMethodResponse> shippingMethodResponseList = new ArrayList();
        for(ShippingMethod shippingMethod : shippingMethodList){
            ShippingMethodResponse shippingMethodResponse = shippingMethodConverter.convertToDto(shippingMethod);
            shippingMethodResponseList.add(shippingMethodResponse);
        }
        return shippingMethodResponseList;
    }
}
