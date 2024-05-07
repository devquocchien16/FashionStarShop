package com.group4.fashionstarshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.group4.fashionstarshop.dto.VariantDTO;
import com.group4.fashionstarshop.model.Variant;
import com.group4.fashionstarshop.request.FindVariantRequest;
import com.group4.fashionstarshop.request.VariantRequest;

public interface VariantService {
	
	VariantDTO getVariantById(Long id);

	VariantDTO createVariant(VariantRequest variantRequest, Long product_id);

	List<VariantDTO> getVariantByProductId(Long product_id);

	Variant findById(Long id);

	Variant createVariant(VariantDTO variantDto, Long product_id);

	VariantDTO getLowestPriceVariantByProductId(Long product_id);

	VariantDTO getVariantByProductPriceMin(Long product_id);

	VariantDTO updateVariant(Long variantId, VariantDTO variantDto);
	

	Page<VariantDTO> getVariantsByContaining(String text, Pageable pageable);

	Page<VariantDTO> getVariantsByNameContainingAndPriceBetween(String text, double minPrice, double maxPrice,
			Pageable pageable);

	Page<VariantDTO> getVariantsBySearchTextAndRating(String text, long star, Pageable pageable);	

	VariantDTO getVariantInfoById(Long id);

	Page<VariantDTO> getNewestVariantsByText(String text, Pageable pageable);

	VariantDTO updateVariant(VariantRequest variantRequest, Long product_id);

	List<VariantDTO> getVariantsByProductId(Long product_id);
	
	List<VariantDTO> createRawVariant(Long product_id);

	VariantDTO getVariantIdByProductIdAndOptionValueIds(Long productId,FindVariantRequest request);
	
	
	VariantDTO deleteVariant(Long variant_id);

	

}
