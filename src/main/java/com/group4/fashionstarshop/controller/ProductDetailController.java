package com.group4.fashionstarshop.controller;

import com.group4.fashionstarshop.dto.*;
import com.group4.fashionstarshop.model.Product;
import com.group4.fashionstarshop.request.FindVariantRequest;
import com.group4.fashionstarshop.request.OptionRequest;
import com.group4.fashionstarshop.request.ProductRequest;
import com.group4.fashionstarshop.payload.ProductDetailResponse;
import com.group4.fashionstarshop.payload.VariantDetailResponse;
import com.group4.fashionstarshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/product-detail")
public class ProductDetailController {

	@Autowired
	private VariantService variantService;

	@PostMapping("/{productId}/{variantId}")
	public ResponseEntity<VariantDTO> getVariantByProductIdAndOptionValueIds(
		@PathVariable("productId") Long productId,
	    @RequestBody FindVariantRequest request) {
	      VariantDTO variantDTO = variantService.getVariantIdByProductIdAndOptionValueIds(productId, request);
	        return ResponseEntity.ok(variantDTO);
	    }

}