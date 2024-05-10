package com.group4.fashionstarshop.service;

import java.util.List;

import com.group4.fashionstarshop.dto.ProductDTO;
import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.model.Store;
import com.group4.fashionstarshop.request.StoreDeclinedRequest;
import com.group4.fashionstarshop.request.StoreRequest;

public interface StoreService {

	StoreDTO findStore(Long id);

	StoreDTO createStore(Long sellerId, StoreRequest request);

	StoreDTO findStoresBySellerId(Long seller_id);
	
	Store declinedStoreRequest(StoreDeclinedRequest storeRequest, Long store_id);
	
	StoreDTO updateStore(Long storeId, StoreRequest request);

	List<StoreDTO> findStoreRequest();

	List<StoreDTO> findInactiveStores();

	

}
