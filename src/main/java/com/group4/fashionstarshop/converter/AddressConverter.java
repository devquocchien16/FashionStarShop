package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.AddressDTO;
import com.group4.fashionstarshop.model.Address;
import com.group4.fashionstarshop.request.AddressRequest;
import com.group4.fashionstarshop.payload.AddressResponse;

import java.util.List;

public interface AddressConverter {
	AddressDTO entityToDTO(Address element);

	Address dtoToEntity(AddressDTO element);

	List<Address> dtosToEntities(List<AddressDTO> element);

	List<AddressDTO> entitiesToDTOs(List<Address> element);
	 AddressResponse convertToDto(Address address);
	    Address convertToEntity(AddressRequest addressRequest);
}
