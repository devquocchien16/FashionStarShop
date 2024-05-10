package com.group4.fashionstarshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group4.fashionstarshop.dto.AttributeDTO;
import com.group4.fashionstarshop.dto.OptionTableDTO;
import com.group4.fashionstarshop.dto.ProductDTO;
import com.group4.fashionstarshop.dto.VariantDTO;
import com.group4.fashionstarshop.model.Attribute;
import com.group4.fashionstarshop.service.AttributeService;
import com.group4.fashionstarshop.service.OptionService;
import com.group4.fashionstarshop.service.OptionValueService;
import com.group4.fashionstarshop.service.ProductService;
import com.group4.fashionstarshop.service.VariantService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private VariantService variantService;
	@Autowired
	private AttributeService attributeService;
	@Autowired
	private OptionService optionService;
	@Autowired
	private OptionValueService optionValueService;

	@GetMapping
	public List<ProductDTO> productDtoList() {
		return productService.getAllProductDtos();
	}

	@GetMapping("/{storeId}/all")
	public List<ProductDTO> getProductsByStore(@PathVariable(name = "storeId") Long storeId) {
		return productService.getAllProductDtosByStore(storeId);
	}

	@GetMapping("/{productId}/details")
	public ProductDTO getProductDetails(@PathVariable(name = "productId") Long productId) {
		return productService.getProductDetails(productId);
	}

	@GetMapping("/{productId}/details/options")
	public List<OptionTableDTO> getProductOptions(@PathVariable(name = "productId") Long productId) {
		return productService.getOptionsByProductId(productId);
	}

	@GetMapping("/{product_id}/details/variants")
	public ResponseEntity<List<VariantDTO>> getVariantsByProductId(@PathVariable("product_id") Long product_id) {
		List<VariantDTO> variantDTOs = variantService.getVariantsByProductId(product_id);
		if (variantDTOs != null) {
			return new ResponseEntity<>(variantDTOs, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(variantDTOs, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{product_id}/details/attributes")
	public ResponseEntity<List<AttributeDTO>> getAttributesByProductId(@PathVariable("product_id") Long product_id) {
		List<AttributeDTO> attributeDTOs = attributeService.geAttributeByProductId(product_id);
		if (attributeDTOs != null) {
			return new ResponseEntity<>(attributeDTOs, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(attributeDTOs, HttpStatus.BAD_REQUEST);
		}
	}


}
