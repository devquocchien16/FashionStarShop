package com.group4.fashionstarshop.converter.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.group4.fashionstarshop.converter.StoreConverter;
import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.model.Store;

@Component
public class StoreConverterImpl implements StoreConverter {
	
	
	@Override
	public StoreDTO entityToDTO(Store element) {
		StoreDTO result = new StoreDTO();
		BeanUtils.copyProperties(element, result);
		//result.setStoreCategoryDTOs(storeCategoryConverter.entitiesToDTOs(element.getStoreCategoryList()));
		return result;
	}

	@Override
	public Store dtoToEntity(StoreDTO element) {
		Store result = new Store();
		BeanUtils.copyProperties(element, result);		
		return result;
	}

	@Override
	public List<Store> dtosToEntities(List<StoreDTO> element) {
		return element.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}

	@Override
	public List<StoreDTO> entitiesToDTOs(List<Store> element) {
		return element.stream().map(this::entityToDTO).collect(Collectors.toList());
	}
 
}
