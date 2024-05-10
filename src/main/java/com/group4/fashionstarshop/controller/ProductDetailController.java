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

import java.util.Calendar;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/seller/product-detail")
public class ProductDetailController {

	@Autowired
	private VariantService variantService;
	private final ImageService imageServiceImpl;
    private final ProductService productServiceImpl;
    private final ProductAttributeService productAttributeServiceImpl;
    private final VariantService variantServiceImpl;
    private final OptionValueService optionValueServiceImpl;
    
    @Autowired
    public ProductDetailController(ImageService imageServiceImpl,
                                           ProductService productServiceImpl,
                                           ProductAttributeService productAttributeServiceImpl,
                                           VariantService variantServiceImpl,
                                           OptionValueService optionValueServiceImpl) {
        this.imageServiceImpl =imageServiceImpl;
        this.productServiceImpl= productServiceImpl;
        this.productAttributeServiceImpl=productAttributeServiceImpl;
        this.variantServiceImpl=variantServiceImpl;
        this.optionValueServiceImpl=optionValueServiceImpl;
    }

    ProductDetailResponse productDetailResponse=new ProductDetailResponse();
    VariantDetailResponse variantDetailResponse =new VariantDetailResponse();	
	@GetMapping("/{product_id}")
    public ResponseEntity<ProductDetailResponse> getProduct(@PathVariable("product_id") Long productId) {
        productDetailResponse.setProductDTO(productServiceImpl.getProductById(productId));
        productDetailResponse.setStoreDto(productServiceImpl.getStoreByProductId(productId));
        productDetailResponse.setOptionTableDto(productServiceImpl.getOptionsByProductId(productId));
        productDetailResponse.setVariantDTOList(variantServiceImpl.getVariantByProductId(productId));
        productDetailResponse.setProductAttributeDTOList(productAttributeServiceImpl.getProductAttributeByProductId(productId));
        return ResponseEntity.ok(productDetailResponse);
    }
	 @GetMapping("/{product_id}/{variant_id}")
	    public ResponseEntity<VariantDetailResponse> getVariant(@PathVariable("variant_id") Long variantId){
	        List<ImageDTO> images = imageServiceImpl.getImageByVariantId(variantId);
	        VariantDTO variantDto = variantServiceImpl.getVariantById(variantId);
	        List<OptionValueDTO> optionValueDTOList = optionValueServiceImpl.getOptionValuesByVariantId(variantId);
	        variantDetailResponse.setVariantDto(variantDto);
	        variantDetailResponse.setImageDTOS(images);
	        variantDetailResponse.setOptionValueDTOS(optionValueDTOList);
	        return ResponseEntity.ok(variantDetailResponse);
	    }

	@PostMapping("/{productId}")
	public ResponseEntity<VariantDTO> getVariantByProductIdAndOptionValueIds(
		@PathVariable("productId") Long productId,
	    @RequestBody FindVariantRequest request) {
	      VariantDTO variantDTO = variantService.getVariantIdByProductIdAndOptionValueIds(productId, request);
	        return ResponseEntity.ok(variantDTO);
	    }
	
	

    ProductDetailResponse productDetailResponse=new ProductDetailResponse();
    VariantDetailResponse variantDetailResponse =new VariantDetailResponse();
	

//	@PostMapping("/{productId}/{variantId}")
//	public ResponseEntity<VariantDTO> getVariantByProductIdAndOptionValueIds(
//		@PathVariable("productId") Long productId,
//	    @RequestBody FindVariantRequest request) {
//	      VariantDTO variantDTO = variantService.getVariantIdByProductIdAndOptionValueIds(productId, request);
//	        return ResponseEntity.ok(variantDTO);
//	    }
	
    @GetMapping("/{product_id}/{variant_id}")
    public ResponseEntity<VariantDetailResponse> getVariant(@PathVariable("variant_id") Long variantId){
        List<ImageDTO> images = imageServiceImpl.getImageByVariantId(variantId);
        VariantDTO variantDto = variantServiceImpl.getVariantById(variantId);
        List<OptionValueDTO> optionValueDTOList = optionValueServiceImpl.getOptionValuesByVariantId(variantId);
        variantDetailResponse.setVariantDto(variantDto);
        variantDetailResponse.setImageDTOS(images);
        variantDetailResponse.setOptionValueDTOS(optionValueDTOList);
        return ResponseEntity.ok(variantDetailResponse);
    }
    
	@GetMapping("/{product_id}")
    public ResponseEntity<ProductDetailResponse> getProduct(@PathVariable("product_id") Long productId) {
        productDetailResponse.setProductDTO(productServiceImpl.getProductById(productId));
        productDetailResponse.setStoreDto(productServiceImpl.getStoreByProductId(productId));
        productDetailResponse.setOptionTableDto(productServiceImpl.getOptionsByProductId(productId));
        productDetailResponse.setVariantDTOList(variantServiceImpl.getVariantByProductId(productId));
        productDetailResponse.setProductAttributeDTOList(productAttributeServiceImpl.getProductAttributeByProductId(productId));
        return ResponseEntity.ok(productDetailResponse);
    }


}