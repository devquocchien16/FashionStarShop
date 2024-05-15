package com.group4.fashionstarshop.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.group4.fashionstarshop.converter.StoreConverter;
import com.group4.fashionstarshop.dto.StoreActiveDTO;
import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.model.Address;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.model.Store;
import com.group4.fashionstarshop.repository.AddressRepository;
import com.group4.fashionstarshop.repository.SellerRepository;
import com.group4.fashionstarshop.repository.StoreRepository;
import com.group4.fashionstarshop.request.StoreDeclinedRequest;
import com.group4.fashionstarshop.request.AddStoreRequest;
import com.group4.fashionstarshop.request.StoreRequest;
import com.group4.fashionstarshop.service.StoreService;

@Service
public class StoreServiceImpl implements StoreService {	
	@Autowired
    private StoreConverter storeConverter;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private AddressRepository addressRepository;
  
    @Override
    public StoreDTO findStore(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("Store not found"));       
        return storeConverter.entityToDTO(store);
    }


    @Override
    public StoreDTO createStore(Long sellerId, AddStoreRequest request) {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(
                () -> new UsernameNotFoundException("seller not found"));
        Store store = new Store();
        store.setName(request.getName());
        store.setLogo(request.getLogo());
        store.setDescription(request.getDescription());
        
        store.setType(request.isType());
        store.setTax_num(request.getTax_num());
        store.setCertificate_image(request.getCertificate_image());
        
        store.setIdentity_type(request.getIdentity_type());
        store.setIdentity_num(request.getIdentity_num());
        store.setIdentity_image_1(request.getIdentity_image_1());
        store.setIdentity_image_2(request.getIdentity_image_2());       
        store.setStatus(false);
        store.setSeller(seller);
        storeRepository.save(store);
        
        Address tax_address = new Address();
        tax_address.setCity(request.getTax_city());
        tax_address.setDistrict(request.getTax_district());
        tax_address.setWard(request.getTax_ward());
        tax_address.setStreet(request.getTax_street());
        tax_address.setSeller(seller);
        addressRepository.save(tax_address);
        
        
        Address pickup_address = new Address();
        pickup_address.setCity(request.getPickup_city());
        pickup_address.setDistrict(request.getPickup_district());
        pickup_address.setWard(request.getPickup_ward());
        pickup_address.setStreet(request.getPickup_street());
        pickup_address.setSeller(seller);
        addressRepository.save(pickup_address);
        
        StoreDTO newStoreDTO = storeConverter.entityToDTO(store);
        return newStoreDTO;
    }



	@Override
	public StoreDTO findStoresBySellerId(Long seller_id) {
		 Seller seller = sellerRepository.findById(seller_id).orElseThrow(
	                () -> new UsernameNotFoundException("seller not found"));
		 Store store = storeRepository.findBySeller(seller);
		 
		return storeConverter.entityToDTO(store);
	}


	@Override
	public StoreDTO updateStore(Long storeId, StoreRequest request) {
	    // Tìm kiếm cửa hàng cần cập nhật
	    Store store = storeRepository.findById(storeId)
	            .orElseThrow();
	    // Thực hiện cập nhật thông tin từ request
	    store.setEditingName(request.getName());
	    store.setEditingLogo(request.getLogo());
	    store.setDescription(request.getDescription());
	    store = storeRepository.save(store);

	    // Chuyển đổi và trả về đối tượng StoreDTO đã được cập nhật
	    return storeConverter.entityToDTO(store);
	}

	@Override
	public Store declinedStoreRequest(StoreDeclinedRequest storeRequest, Long store_id) {
		 Optional<Store> storeOptional = storeRepository.findByStoreId(store_id);
	            Store store = storeOptional.get();
	            store.setStatus(false);
	            store.setRejectedReason(storeRequest.getRejectedReason());
	            storeRepository.save(store);
	            System.out.println("Store: "+ store.getRejectedReason() + store.getId());
	            return store;
 }

	@Override
	public List<StoreDTO> findInactiveStores() {
	    List<Store> inactiveStores = storeRepository.findByStatus(false);
	    return storeConverter.entitiesToDTOs(inactiveStores);
	}


	@Override
	public List<StoreDTO> findStoreRequest() {
		// TODO Auto-generated method stub
		return null;
	}


    public StoreActiveDTO confirmEditingNameAndLogo(StoreActiveDTO request, Long storeId) {
    	Store store = storeRepository.findEditingNameEditingLogoActiveStoreById(storeId);
        if (store != null) {
            store.setName(store.getEditingName());
            store.setLogo(store.getEditingLogo());
            store.setAdminReply(request.getAdminReply());
            store.setEditingName(null);
            store.setEditingLogo(null);
            storeRepository.save(store);
            System.out.println("Updated Store Logo: " + store.getLogo());

            // Create a response object and populate it with the store's data
            StoreActiveDTO response = new StoreActiveDTO();
            response.setId(store.getId());
            response.setName(store.getName());
            response.setLogo(store.getLogo());
            response.setEditingName(store.getEditingName()); // This will be null
            response.setEditingLogo(store.getEditingLogo()); // This will be null
            response.setStatus(store.isStatus());
            response.setAdminReply(store.getAdminReply());
            return response;
        } else {
            // Throw an exception if the store was not found
            throw new RuntimeException("Active store with editing name and logo not found with id " + storeId);
        }
    }
    
    public StoreActiveDTO declineEditingNameAndLogo(StoreActiveDTO request, Long storeId) {
    	Store store = storeRepository.findEditingNameEditingLogoActiveStoreById(storeId);
        if (store != null) {
            store.setName(store.getName());
            store.setLogo(store.getLogo());
            store.setAdminReply(request.getAdminReply());
            store.setEditingName(store.getEditingName());
            store.setEditingLogo(store.getEditingName());
            storeRepository.save(store);
            StoreActiveDTO response = new StoreActiveDTO();
            response.setId(store.getId());
            response.setName(store.getName());
            response.setLogo(store.getLogo());
            response.setEditingName(store.getEditingName()); // This will be null
            response.setEditingLogo(store.getEditingLogo()); // This will be null
            response.setStatus(store.isStatus());
            response.setAdminReply(store.getAdminReply());
            return response;
        } else {
            // Throw an exception if the store was not found
            throw new RuntimeException("Active store with editing name and logo not found with id " + storeId);
        }
    }
}
