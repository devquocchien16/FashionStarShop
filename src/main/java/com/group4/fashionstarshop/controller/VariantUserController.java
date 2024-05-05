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

import com.group4.fashionstarshop.dto.VariantDTO;
import com.group4.fashionstarshop.model.Variant;
import com.group4.fashionstarshop.payload.VariantCreateResponse;
import com.group4.fashionstarshop.payload.VariantRawResponse;
import com.group4.fashionstarshop.request.VariantRequest;
import com.group4.fashionstarshop.service.implement.VariantServiceImpl;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/variant")
public class VariantUserController {
	  private final VariantServiceImpl variantService;

	    @Autowired
	    public VariantUserController(VariantServiceImpl variantService ) {
	        this.variantService = variantService;
	    }	

	    @GetMapping("/{productId}")
	    public ResponseEntity<VariantDTO> getVariantById(@PathVariable("productId")Long variantId){
	        VariantDTO variantDTO = variantService.getVariantById(variantId);
	        return new ResponseEntity<>(variantDTO, HttpStatus.OK);
	    }

}
