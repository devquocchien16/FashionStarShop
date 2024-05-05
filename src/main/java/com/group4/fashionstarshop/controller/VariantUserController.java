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

	    //createVariantByProductId, and ListValue
	    @PostMapping("/{product_id}/create")
	    public ResponseEntity<VariantRawResponse> createVariantWithListValue(@RequestBody List<Long> valueIdList, @PathVariable("product_id") Long product_id){
	        VariantRawResponse variantRawResponse= new VariantRawResponse();
	        VariantDTO variantDto = variantService.createRawVariant(valueIdList,product_id);
	        if (variantDto != null) {
	            variantRawResponse.setMessage("OptionValue created successfully");
	            variantRawResponse.setVariantDto(variantDto);
	            return new ResponseEntity<>(variantRawResponse, HttpStatus.CREATED);
	        } else {
	            variantRawResponse.setMessage("Failed to create Variant");
	            return new ResponseEntity<>(variantRawResponse,HttpStatus.BAD_REQUEST);
	        }
	    }
	    @PostMapping("/{product_id}/create1")
	    public ResponseEntity<VariantCreateResponse> createVariant(@RequestBody VariantRequest variantRequest,@PathVariable("product_id") Long product_id){
	        VariantCreateResponse variantCreateResponse= new VariantCreateResponse();
	        VariantDTO variantDto = VariantDTO.builder()
	                .name(variantRequest.getName())
	                .skuCode(variantRequest.getSkuCode())
	                .stockQuantity(variantRequest.getStockQuantity())
	                .weight(variantRequest.getWeight())
	                .price(variantRequest.getPrice())
	                .salePrice(variantRequest.getSalePrice())
	                .img(variantRequest.getImg())
	                .build();

	         Variant variant =variantService.createVariant(variantDto,product_id);

	        if (variant != null) {
	            variantCreateResponse.setMessage("Variant created successfully");
	            variantCreateResponse.setVariant_id(variant.getId());
	            return new ResponseEntity<>(variantCreateResponse, HttpStatus.CREATED);
	        } else {
	            variantCreateResponse.setMessage("Failed to create Variant");
	            return new ResponseEntity<>(variantCreateResponse,HttpStatus.BAD_REQUEST);
	        }
	    }
	    @PutMapping("/update/{variant_id}")
	    public ResponseEntity<VariantDTO> updateVariant(@PathVariable("variant_id") Long variant_id,@RequestBody VariantRequest variantRequest){
//	        VariantDTO variantDto = variantService.getVariantById(variant_id);
//	        if (variantDto == null) {
//	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	        }
	        VariantDTO variantDto = VariantDTO.builder()
	                .name(variantRequest.getName())
	                .skuCode(variantRequest.getSkuCode())
	                .stockQuantity(variantRequest.getStockQuantity())
	                .weight(variantRequest.getWeight())
	                .price(variantRequest.getPrice())
	                .salePrice(variantRequest.getSalePrice())
	                .img(variantRequest.getImg())
	                .build();

	        VariantDTO variantDTO = variantService.updateVariant(variant_id,variantDto);
	        if (variantDTO != null) {
	            return new ResponseEntity<>(variantDTO, HttpStatus.CREATED);
	        } else {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	    }
	   
//	    @DeleteMapping("/delete/{variant_id}")
//	    public ResponseEntity<Long> deleteVariant(@PathVariable("variant_id") Long variant_Id) {
//	        VariantDTO variantDto = variantService.getVariantById(variant_Id);
//	        if (variantDto == null) {
//	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	        }
//	        try {
//	            variantService.deleteVariant(variant_Id);
//	            return new ResponseEntity<>(variant_Id, HttpStatus.OK);
//	        } catch (Exception e) {
//	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//	        }
//	    }

	    @GetMapping("/{productId}")
	    public ResponseEntity<VariantDTO> getVariantById(@PathVariable("productId")Long productId){
	        VariantDTO variantDTO = variantService.getLowestPriceVariantByProductId(productId);
	        return new ResponseEntity<>(variantDTO, HttpStatus.OK);
	    }

}
