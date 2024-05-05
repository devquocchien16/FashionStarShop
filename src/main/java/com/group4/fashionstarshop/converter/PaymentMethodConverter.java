package com.group4.fashionstarshop.converter;

import java.util.List;

import com.group4.fashionstarshop.dto.PaymentMethodDTO;
import com.group4.fashionstarshop.model.PaymentMethod;
import com.group4.fashionstarshop.payload.PaymentMethodResponse;
import com.group4.fashionstarshop.request.PaymentMethodRequest;


public interface PaymentMethodConverter {
	PaymentMethodDTO entityToDTO(PaymentMethod element);

	PaymentMethod dtoToEntity(PaymentMethodDTO element);

	List<PaymentMethod> dtosToEntities(List<PaymentMethodDTO> element);

	List<PaymentMethodDTO> entitiesToDTOs(List<PaymentMethod> element);
	  PaymentMethodResponse convertToDto(PaymentMethod paymentMethod);
	    PaymentMethod convertToEntity(PaymentMethodRequest paymentMethodRequest);
}
