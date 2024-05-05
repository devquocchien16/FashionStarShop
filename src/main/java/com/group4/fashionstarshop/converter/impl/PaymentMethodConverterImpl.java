package com.group4.fashionstarshop.converter.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.group4.fashionstarshop.converter.PaymentMethodConverter;
import com.group4.fashionstarshop.dto.PaymentMethodDTO;
import com.group4.fashionstarshop.model.PaymentMethod;
import com.group4.fashionstarshop.payload.PaymentMethodResponse;
import com.group4.fashionstarshop.request.PaymentMethodRequest;

@Component
public class PaymentMethodConverterImpl implements PaymentMethodConverter {

	@Override
	public PaymentMethodDTO entityToDTO(PaymentMethod element) {
		PaymentMethodDTO result = new PaymentMethodDTO();
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public PaymentMethod dtoToEntity(PaymentMethodDTO element) {
		PaymentMethod result = new PaymentMethod();
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public List<PaymentMethod> dtosToEntities(List<PaymentMethodDTO> element) {
		return element.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}

	@Override
	public List<PaymentMethodDTO> entitiesToDTOs(List<PaymentMethod> element) {
		return element.stream().map(this::entityToDTO).collect(Collectors.toList());
	}

	  @Override
	    public PaymentMethodResponse convertToDto(PaymentMethod paymentMethod) {
	        return PaymentMethodResponse
	                .builder()
	                .id(paymentMethod.getId())
	                .cartNumber(paymentMethod.getCartNumber())
	                .nameOnCard(paymentMethod.getNameOnCard())
	                .expirationDate(paymentMethod.getExpirationDate())
	                .defaultPayment(paymentMethod.getDefaultPayment())
	                .build();
	    }

	    @Override
	    public PaymentMethod convertToEntity(PaymentMethodRequest paymentMethodRequest) {
	        PaymentMethod paymentMethod = new PaymentMethod();
	        BeanUtils.copyProperties(paymentMethodRequest,paymentMethod);
	        return paymentMethod;
	    }
   
}
