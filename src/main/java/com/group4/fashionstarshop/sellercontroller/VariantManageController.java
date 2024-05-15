package com.group4.fashionstarshop.sellercontroller;

import com.group4.fashionstarshop.dto.VariantDTO;
import com.group4.fashionstarshop.model.Variant;
import com.group4.fashionstarshop.request.VariantRequest;
import com.group4.fashionstarshop.payload.VariantCreateResponse;
import com.group4.fashionstarshop.payload.VariantRawResponse;
import com.group4.fashionstarshop.service.VariantService;
import com.group4.fashionstarshop.service.implement.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller/variant")
public class VariantManageController {
	@Autowired
	private VariantService variantService;

	@PostMapping("/{product_id}/create")
	public ResponseEntity<List<VariantDTO>> createRawVariant(@PathVariable(name = "product_id") Long product_id) {
		List<VariantDTO> variantDTOs = variantService.createRawVariant(product_id);
		return new ResponseEntity<>(variantDTOs, HttpStatus.CREATED);
	}

	@PostMapping("/update/{variant_id}")
	public ResponseEntity<VariantDTO> updateVariant(@RequestBody VariantRequest variantRequest,
			@PathVariable("variant_id") Long variant_id) {
		VariantDTO variantDTO = variantService.updateVariant(variantRequest, variant_id);
		if (variantDTO != null) {
			return new ResponseEntity<>(variantDTO, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(variantDTO, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete/{variant_id}")
	public ResponseEntity<VariantDTO> deleteVariant(@PathVariable("variant_id") Long variant_id) {
		VariantDTO variantDTO = variantService.deleteVariant(variant_id);
		return new ResponseEntity<>(variantDTO, HttpStatus.ACCEPTED);
	}

}
