package com.group4.fashionstarshop.service;

import com.group4.fashionstarshop.dto.OptionValueDTO;
import com.group4.fashionstarshop.dto.VariantDTO;
import com.group4.fashionstarshop.model.Variant;
import com.group4.fashionstarshop.request.FindVariantRequest;
import com.group4.fashionstarshop.request.VariantRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VariantService {
	
	VariantDTO getVariantById(Long id);

	VariantDTO createVariant(VariantRequest variantRequest, Long product_id);

	List<VariantDTO> getVariantByProductId(Long product_id);

	Variant findById(Long id);

	Variant createVariant(VariantDTO variantDto, Long product_id);

	VariantDTO getLowestPriceVariantByProductId(Long product_id);

	VariantDTO getVariantByProductPriceMin(Long product_id);

	VariantDTO updateVariant(Long variantId, VariantDTO variantDto);

	void deleteVariant(Long variantId);

	Page<VariantDTO> getVariantsByContaining(String text, Pageable pageable);

	Page<VariantDTO> getVariantsByNameContainingAndPriceBetween(String text, double minPrice, double maxPrice,
			Pageable pageable);

	Page<VariantDTO> getVariantsBySearchTextAndRating(String text, long star, Pageable pageable);

	VariantDTO createRawVariant(List<Long> valueIdList, Long productId);

	VariantDTO getVariantInfoById(Long id);

	Page<VariantDTO> getNewestVariantsByText(String text, Pageable pageable);

	VariantDTO updateVariant(VariantRequest variantRequest, Long product_id);

	List<VariantDTO> getVariantsByProductId(Long product_id);

	//List<OptionValueDTO> getOptionValuesByVariantId(Long variant_id);
	List<VariantDTO> createRawVariant(Long product_id);

	VariantDTO getVariantIdByProductIdAndOptionValueIds(Long productId, FindVariantRequest request);

}
