package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.dto.StoreRegisterDTO;
import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.model.Store;
import com.group4.fashionstarshop.model.Store;

import java.util.List;

public interface StoreConverter {
	StoreDTO entityToDTO(Store element);

	Store dtoToEntity(StoreDTO element);

	List<Store> dtosToEntities(List<StoreDTO> element);

	List<StoreDTO> entitiesToDTOs(List<Store> element);
	StoreRegisterDTO convertToDto(Store store);
}
