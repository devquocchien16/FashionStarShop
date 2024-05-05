package com.group4.fashionstarshop.converter.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.group4.fashionstarshop.converter.StoreCategoryConverter;
import com.group4.fashionstarshop.dto.StoreCategoryDTO;
import com.group4.fashionstarshop.dto.CategoryDTO;
import com.group4.fashionstarshop.model.StoreCategory;

@Component
public class StoreCategoryConverterImpl implements StoreCategoryConverter {

	@Override
	public StoreCategoryDTO entityToDTO(StoreCategory element) {
		StoreCategoryDTO result = new StoreCategoryDTO();
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(element.getCategory().getId());
		categoryDTO.setName(element.getCategory().getName());
		result.setCategoryDTO(categoryDTO);
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public StoreCategory dtoToEntity(StoreCategoryDTO element) {
		StoreCategory result = new StoreCategory();
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public List<StoreCategory> dtosToEntities(List<StoreCategoryDTO> element) {
		return element.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}

	@Override
	public List<StoreCategoryDTO> entitiesToDTOs(List<StoreCategory> element) {
		return element.stream().map(this::entityToDTO).collect(Collectors.toList());
	}
}
