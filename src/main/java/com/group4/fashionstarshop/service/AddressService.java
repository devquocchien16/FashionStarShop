package com.group4.fashionstarshop.service;

import com.group4.fashionstarshop.request.AddressRequest;
import com.group4.fashionstarshop.dto.AddressDTO;
import com.group4.fashionstarshop.payload.AddressResponse;

import java.util.List;

public interface AddressService {    
    AddressDTO updateAddress(Long address_id, AddressRequest request);
	AddressDTO createUserAddress(Long user_id, AddressRequest request);
	AddressDTO createSellerAddress(Long seller_id, AddressRequest request);
	List<AddressDTO> getSellerAddress(Long seller_id);
	 List<AddressResponse> findAddress(Long userId);
	void deleteAddress(Long address_id);
	 AddressResponse createAddress(AddressRequest addressRequest);
	    AddressResponse updateAddress(AddressRequest addressRequest);
	
	
}
