package com.group4.fashionstarshop.service;

import com.group4.fashionstarshop.dto.StoreCategoryDTO;
import com.group4.fashionstarshop.request.StoreCategoryResquest;

import java.util.List;

public interface StoreCategoryService {
    List<StoreCategoryDTO> getStoreCategoryListByStoreId(Long storeId);   
	StoreCategoryDTO createStoreCategory(StoreCategoryResquest request, Long storeId);
}
