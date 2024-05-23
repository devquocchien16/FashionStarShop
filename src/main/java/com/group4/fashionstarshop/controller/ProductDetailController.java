package com.group4.fashionstarshop.controller;

import com.group4.fashionstarshop.dto.*;
import com.group4.fashionstarshop.model.Product;
import com.group4.fashionstarshop.model.ProductAttribute;
import com.group4.fashionstarshop.model.Variant;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/product-detail")
public class ProductDetailController {

	@Autowired
	private VariantService variantService;
	private final ImageService imageServiceImpl;
	private final ProductService productServiceImpl;
	private final ProductAttributeService productAttributeServiceImpl;
	private final VariantService variantServiceImpl;
	private final OptionValueService optionValueServiceImpl;
	@Autowired
	private ProductService productService;
	@Autowired
	private ReviewService reviewService;

	@Autowired
	public ProductDetailController(ImageService imageServiceImpl, ProductService productServiceImpl,
			ProductAttributeService productAttributeServiceImpl, VariantService variantServiceImpl,
			OptionValueService optionValueServiceImpl) {
		this.imageServiceImpl = imageServiceImpl;
		this.productServiceImpl = productServiceImpl;
		this.productAttributeServiceImpl = productAttributeServiceImpl;
		this.variantServiceImpl = variantServiceImpl;
		this.optionValueServiceImpl = optionValueServiceImpl;
	}

	ProductDetailResponse productDetailResponse = new ProductDetailResponse();
	VariantDetailResponse variantDetailResponse = new VariantDetailResponse();

	@GetMapping("/{product_id}")
	public ResponseEntity<ProductDetailResponse> getProduct(@PathVariable("product_id") Long productId) {
		productDetailResponse.setProductDTO(productServiceImpl.getProductById(productId));
		productDetailResponse.setStoreDto(productServiceImpl.getStoreByProductId(productId));
		productDetailResponse.setOptionTableDto(productServiceImpl.getOptionsByProductId(productId));

		List<VariantDTO> listResult = new ArrayList<>();
		listResult = variantServiceImpl.getVariantByProductId(productId);
		for (var item : listResult) {
			List<OptionValueDTO> optionValueDTOList = optionValueServiceImpl.getOptionValuesByVariantId(item.getId());
			List<ImageDTO> images = imageServiceImpl.getImageByVariantId(item.getId());
			List<ReviewDTO> reviews = reviewService.getReviewsByVariantId(item.getId());
			item.setReviewDTOList(reviews);
			item.setImageDTOList(images);
			item.setOptionValueDTOList(optionValueDTOList);

		}
		VariantDTO result = listResult.get(0);
		List<OptionValueDTO> optionValueDTOList = optionValueServiceImpl.getOptionValuesByVariantId(result.getId());
		List<ImageDTO> images = imageServiceImpl.getImageByVariantId(result.getId());
		List<ReviewDTO> reviews = reviewService.getReviewsByVariantId(result.getId());
		result.setReviewDTOList(reviews);
		result.setImageDTOList(images);
		result.setOptionValueDTOList(optionValueDTOList);
		productDetailResponse.setVariantDto(result);
		productDetailResponse.setVariantDTOList(listResult);

		productDetailResponse
				.setProductAttributeDTOList(productAttributeServiceImpl.getProductAttributeByProductId(productId));
		return ResponseEntity.ok(productDetailResponse);
	}

	@GetMapping("/{product_id}/{variant_id}")
	public ResponseEntity<VariantDetailResponse> getVariant(@PathVariable("variant_id") Long variantId) {
		List<ImageDTO> images = imageServiceImpl.getImageByVariantId(variantId);
		VariantDTO variantDto = variantServiceImpl.getVariantById(variantId);
		List<OptionValueDTO> optionValueDTOList = optionValueServiceImpl.getOptionValuesByVariantId(variantId);
		variantDetailResponse.setVariantDto(variantDto);
		variantDetailResponse.setImageDTOS(images);
		variantDetailResponse.setOptionValueDTOS(optionValueDTOList);
		return ResponseEntity.ok(variantDetailResponse);
	}

	@PostMapping("/{productId}")
	public ResponseEntity<VariantDTO> getVariantByProductIdAndOptionValueIds(@PathVariable("productId") Long productId,
			@RequestBody FindVariantRequest request) {
		VariantDTO variantDTO = variantService.getVariantIdByProductIdAndOptionValueIds(productId, request);
		return ResponseEntity.ok(variantDTO);
	}


	@GetMapping("/{productId}/details/options")
	public List<OptionTableDTO> getProductOptions(@PathVariable(name = "productId") Long productId) {
		return productService.getOptionsByProductId(productId);
	}

	// dung de lay variant detail
	@GetMapping("/variant/{variant_id}")
	public ResponseEntity<VariantDTO> getVariantById(@PathVariable("variant_id") Long variant_id) {
		VariantDTO variantDTO = variantService.getVariantById(variant_id);
		return ResponseEntity.ok(variantDTO);
	}
}