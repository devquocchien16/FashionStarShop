package com.group4.fashionstarshop.converter.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.group4.fashionstarshop.converter.StoreCategoryConverter;
import com.group4.fashionstarshop.converter.StoreConverter;
import com.group4.fashionstarshop.dto.StoreDTO;
import com.group4.fashionstarshop.dto.StoreRegisterDTO;
import com.group4.fashionstarshop.model.Store;

@Component
public class StoreConverterImpl implements StoreConverter {

	@Autowired
	private StoreCategoryConverter storeCategoryConverter;
	
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
		result.setStoreCategoryList(storeCategoryConverter.dtosToEntities(element.getStoreCategoryDTOs()));
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

	@Override
	public StoreRegisterDTO convertToDto(Store store) {
		StoreRegisterDTO storeRegisterDTO = new StoreRegisterDTO();
        storeRegisterDTO.setId(store.getId());
        storeRegisterDTO.setName(store.getName());
        storeRegisterDTO.setLogo(store.getLogo());
        storeRegisterDTO.setEdittingName(store.getEditingName());
        storeRegisterDTO.setStatus(store.isStatus());
        storeRegisterDTO.setType(store.isType());
        storeRegisterDTO.setCertificate_image(store.getCertificate_image());
        storeRegisterDTO.setIdentity_image_1(store.getIdentity_image_1());
        storeRegisterDTO.setIdentity_image_2(store.getIdentity_image_2());
        storeRegisterDTO.setIdentity_type(store.getIdentity_type());
        storeRegisterDTO.setTax_num(store.getTax_num());
        storeRegisterDTO.setIdentity_num(store.getIdentity_num());
        storeRegisterDTO.setRejectedReason(store.getRejectedReason());
        // Các bước khác cần thiết để chuyển đổi đối tượng Seller thành SellerDTO nếu cần

        return storeRegisterDTO;
	}
 
}
