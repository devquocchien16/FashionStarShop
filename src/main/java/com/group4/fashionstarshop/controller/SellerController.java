package com.group4.fashionstarshop.controller;

import com.group4.fashionstarshop.converter.SellerConverter;
import com.group4.fashionstarshop.dto.SellerDTO;
import com.group4.fashionstarshop.model.Seller;
import com.group4.fashionstarshop.repository.SellerRepository;
import com.group4.fashionstarshop.request.UpdateSellerRequest;
import com.group4.fashionstarshop.service.SellerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/sellers")
public class SellerController {  
    @Autowired
    private SellerService sellerService;   

    @GetMapping("/{seller_id}")
    public ResponseEntity<SellerDTO> getUser(@PathVariable("seller_id") Long sellerId){
    
        SellerDTO sellerDTO = sellerService.findSeller(sellerId);
        return ResponseEntity.ok(sellerDTO);
    }
    @PostMapping("/{seller_id}/update-profile")
    public ResponseEntity<SellerDTO> updateSeller(@PathVariable("seller_id") Long sellerId, @RequestBody UpdateSellerRequest request){
    	SellerDTO sellerDTO = sellerService.updateSeller(sellerId, request);
        return ResponseEntity.ok(sellerDTO);
    }
}
