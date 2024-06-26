package com.group4.fashionstarshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group4.fashionstarshop.dto.ProductDTO;
import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.repository.ProductRepository;
import com.group4.fashionstarshop.repository.StoreRepository;
import com.group4.fashionstarshop.request.StoreRequest;
import com.group4.fashionstarshop.service.ProductService;
import com.group4.fashionstarshop.service.StoreCategoryService;
import com.group4.fashionstarshop.service.StoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group4.fashionstarshop.dto.ProductDTO;
import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.repository.ProductRepository;
import com.group4.fashionstarshop.repository.StoreRepository;
import com.group4.fashionstarshop.request.AddStoreRequest;
import com.group4.fashionstarshop.service.ProductService;
import com.group4.fashionstarshop.service.StoreCategoryService;
import com.group4.fashionstarshop.service.StoreService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/stores")
public class StoreController {
    @Autowired
    StoreService storeService;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;
    
    @Autowired 
    private StoreCategoryService storeCategoryService;

    @GetMapping("/{store_id}")
    public ResponseEntity<StoreDTO> getStore(@PathVariable("store_id") Long storeId) {
        StoreDTO storeDto = storeService.findStore(storeId);
        return new ResponseEntity<>(storeDto, HttpStatus.OK);
    }     


    @GetMapping("/{store_id}/products")
    public ResponseEntity<List<ProductDTO>> getProductsByStore(@PathVariable("store_id") Long storeId){
        List<ProductDTO> productDTOList = productService.getAllProductDtosByStore(storeId);
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }


    @PostMapping("/create/{sellerId}")
    @Transactional
    public ResponseEntity<StoreDTO> createStore(@PathVariable("sellerId") Long sellerId, @RequestBody StoreRequest request){
        StoreDTO storeDto1 = storeService.createStore(sellerId,request);
        return new ResponseEntity<>(storeDto1,HttpStatus.OK);
    }
    
    @GetMapping("/seller/{seller_id}")
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
