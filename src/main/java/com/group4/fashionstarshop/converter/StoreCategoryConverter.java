package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.StoreCategoryDTO;
import com.group4.fashionstarshop.model.StoreCategory;

import java.util.List;

public interface StoreCategoryConverter {
	StoreCategoryDTO entityToDTO(StoreCategory element);

	StoreCategory dtoToEntity(StoreCategoryDTO element);

	List<StoreCategory> dtosToEntities(List<StoreCategoryDTO> element);

	List<StoreCategoryDTO> entitiesToDTOs(List<StoreCategory> element);

}
