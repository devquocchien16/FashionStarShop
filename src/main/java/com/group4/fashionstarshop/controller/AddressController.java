package com.group4.fashionstarshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group4.fashionstarshop.dto.AddressDTO;
import com.group4.fashionstarshop.request.AddressRequest;
import com.group4.fashionstarshop.service.AddressService;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/address")
public class AddressController {
    @Autowired
    AddressService addressService;   

    @PostMapping("/user/{user_id}/add")
    public ResponseEntity<AddressDTO> createUserAddress(@PathVariable("user_id") Long user_id, @RequestBody AddressRequest request) {
    	AddressDTO addressDTO = addressService.createUserAddress(user_id, request);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }  
    @PostMapping("/seller/{seller_id}/add")
    public ResponseEntity<AddressDTO> createSellerAddress(@PathVariable("seller_id") Long seller_id, @RequestBody AddressRequest request) {
    	AddressDTO addressDTO = addressService.createSellerAddress(seller_id, request);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }  
    @GetMapping("/seller/{seller_id}")
    public ResponseEntity<List<AddressDTO>> getSellerAddress(@PathVariable("seller_id") Long seller_id) {
    	List<AddressDTO> addressDTOs = addressService.getSellerAddress(seller_id);
        return new ResponseEntity<>(addressDTOs, HttpStatus.OK);
    }  
    
    @PutMapping("/{address_id}/update")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable("address_id") Long address_id,@RequestBody AddressRequest request) {
    	AddressDTO addressDTO = addressService.updateAddress(address_id, request);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }  

    @DeleteMapping("/{address_id}/delete")
    public ResponseEntity<?> deleteAddress(@PathVariable("address_id") Long address_id) {
    	addressService.deleteAddress(address_id);
        return new ResponseEntity<>( HttpStatus.OK);
    }  

   


}
