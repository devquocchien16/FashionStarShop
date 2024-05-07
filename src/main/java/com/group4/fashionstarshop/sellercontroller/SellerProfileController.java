package com.group4.fashionstarshop.sellercontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group4.fashionstarshop.dto.SellerDTO;
import com.group4.fashionstarshop.request.UpdateSellerRequest;
import com.group4.fashionstarshop.service.SellerService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/seller/profile")
public class SellerProfileController {  
    @Autowired
    private SellerService sellerService;   

    @GetMapping("/{seller_id}")
    public ResponseEntity<SellerDTO> getUser(@PathVariable("seller_id") Long sellerId){    
        SellerDTO sellerDTO = sellerService.findSeller(sellerId);
        return ResponseEntity.ok(sellerDTO);
    }
    @PostMapping("/update/{seller_id}")
    public ResponseEntity<SellerDTO> updateSeller(@PathVariable("seller_id") Long sellerId, @RequestBody UpdateSellerRequest request){
    	SellerDTO sellerDTO = sellerService.updateSeller(sellerId, request);
        return ResponseEntity.ok(sellerDTO);
    }
}