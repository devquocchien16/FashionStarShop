package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.converter.AddressConverter;
import com.group4.fashionstarshop.dto.AddressDTO;
import com.group4.fashionstarshop.model.Address;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.User;
import com.group4.fashionstarshop.request.AddressRequest;
import com.group4.fashionstarshop.payload.*;
import com.group4.fashionstarshop.repository.AddressRepository;
import com.group4.fashionstarshop.repository.SellerRepository;
import com.group4.fashionstarshop.repository.UserRepository;
import com.group4.fashionstarshop.service.AddressService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private  ModelMapper modelMapper;
    @Autowired
    private AddressConverter addressConverter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerRepository sellerRepository;
	@Override
	public AddressDTO createUserAddress(Long user_id, AddressRequest request) {
		  User user = userRepository.findById(user_id)
	                .orElseThrow();
	        // Create Address entity from AddressRequest
	        Address address = modelMapper.map(request, Address.class);

	        // Associate the address with the user
	        address.setUser(user);
	        // Save the address
	        Address savedAddress = addressRepository.save(address);         

	        // Convert the saved address entity to AddressDTO
	        return modelMapper.map(savedAddress, AddressDTO.class);
	    }	

//	@Override
//	public AddressDTO createSellerAddress(Long seller_id, AddressRequest request) {
//		 Seller seller = sellerRepository.findById(seller_id)
//	                .orElseThrow();
//	        // Create Address entity from AddressRequest
//	        Address address = modelMapper.map(request, Address.class);
//	        // Associate the address with the user
//	        address.setSeller(seller);
//	        // Save the address
//	        Address savedAddress = addressRepository.save(address);       
//
//	        // Convert the saved address entity to AddressDTO
//	        return modelMapper.map(savedAddress, AddressDTO.class);
//	}	

	@Override
	public AddressDTO updateAddress(Long address_id, AddressRequest addressRequest) {
	    Address address = addressRepository.findById(address_id).orElseThrow();    
		        
	        // Đặt địa chỉ mới làm defaultAddress
	        address.setDistrict(addressRequest.getDistrict());
	        address.setWard(addressRequest.getWard());
	        address.setCity(addressRequest.getCity());
	        address.setStreet(addressRequest.getStreet());
	        address.setDefaultAddress(true);
	        
	        Address addressNew = addressRepository.save(address);
	        return addressConverter.entityToDTO(addressNew);     
	}




	@Override
	public void deleteAddress(Long address_id) {
		addressRepository.deleteById(address_id);		
	}

	 @Override
	    public List<AddressResponse> findAddress(Long userId) {
	        List<Address> addressList = addressRepository.findByUserId(userId);
	        List<AddressResponse> addressResponseList = new ArrayList<>();
	        for(Address address: addressList){
	            AddressResponse addressResponse = addressConverter.convertToDto(address);
	            addressResponseList.add(addressResponse);
	        }
	        return addressResponseList;
	    }

	    @Override
	    public AddressResponse updateAddressOrder(AddressRequest addressRequest) {
	        Address address = addressRepository.findById(addressRequest.getId()).get();
	        address.setDistrict(addressRequest.getDistrict());
	        address.setWard(addressRequest.getWard());
	        address.setCity(addressRequest.getCity());
	        address.setStreet(addressRequest.getStreet());
	        Address addressNew = addressRepository.save(address);
	        return addressConverter.convertToDto(addressNew);
	    }
		 @Override
		    public AddressResponse createAddressOrder(Long userId,AddressRequest addressRequest) {
		        User user = userRepository.findById(userId)
		                .orElseThrow();
		        Address address = modelMapper.map(addressRequest, Address.class);
		        address.setUser(user);
		        Address addressNew = addressRepository.save(address);
		        return modelMapper.map(addressNew, AddressResponse.class);
		    }
}
