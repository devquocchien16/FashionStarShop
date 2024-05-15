package com.group4.fashionstarshop.sellercontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.request.AddStoreRequest;
import com.group4.fashionstarshop.request.StoreRequest;
import com.group4.fashionstarshop.service.StoreService;

@RestController
@RequestMapping("api/seller/store")
public class StoreManageController {
    @Autowired
    StoreService storeService; 
     
    @PostMapping("/{seller_id}/create")
    public ResponseEntity<StoreDTO> createStore(@PathVariable("seller_id") Long seller_id, @RequestBody AddStoreRequest request) {
    	
    	StoreDTO storeDto = storeService.createStore(seller_id, request);
        return new ResponseEntity<>(storeDto, HttpStatus.OK);
    }   
    
    @GetMapping("/{seller_id}")
    public ResponseEntity<StoreDTO> getStoresBySellerId(@PathVariable("seller_id") Long seller_id) {
    	StoreDTO storeDto = storeService.findStoresBySellerId(seller_id);
        return new ResponseEntity<>(storeDto, HttpStatus.OK);
    }   
    
    @PutMapping("/update/{store_id}")
    public ResponseEntity<StoreDTO> updateStore(@PathVariable("store_id") Long store_id, @RequestBody StoreRequest request) {
    	StoreDTO storeDto = storeService.updateStore(store_id, request);
        return new ResponseEntity<>(storeDto, HttpStatus.OK);
    }   


}
