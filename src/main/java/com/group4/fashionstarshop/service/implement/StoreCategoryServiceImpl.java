package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.converter.StoreCategoryConverter;
import com.group4.fashionstarshop.dto.StoreCategoryDTO;
import com.group4.fashionstarshop.model.Store;
import com.group4.fashionstarshop.model.StoreCategory;
import com.group4.fashionstarshop.model.Category;
import com.group4.fashionstarshop.repository.CategoryRepository;
import com.group4.fashionstarshop.repository.StoreCategoryRepository;
import com.group4.fashionstarshop.repository.StoreRepository;
import com.group4.fashionstarshop.request.StoreCategoryResquest;
import com.group4.fashionstarshop.service.StoreCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoreCategoryServiceImpl implements StoreCategoryService {
	@Autowired
	private StoreCategoryRepository storeCategoryRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private StoreCategoryConverter storeCategoryConverter;	

	@Override
	public List<StoreCategoryDTO> getStoreCategoryListByStoreId(Long storeId) {

		Store store = storeRepository.findById(storeId)
				.orElseThrow(() -> new EntityNotFoundException("Store not found"));
		List<StoreCategory> storeCategories = storeCategoryRepository.findAllByStore(store);
		return storeCategoryConverter.entitiesToDTOs(storeCategories);
	}

	@Override
	public StoreCategoryDTO createStoreCategory(StoreCategoryResquest request, Long storeId) {		
		  Store store = storeRepository.findById(storeId)
	                .orElseThrow(() -> new EntityNotFoundException("Store not found"));

	        Category category = categoryRepository.findById(request.getCategory_id())
	                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
	        
	        
	        StoreCategory storeCategory = new StoreCategory();
	        storeCategory.setName(request.getName());
	        storeCategory.setCategory(category);
	        storeCategory.setStore(store);

	        StoreCategory savedStoreCategory = storeCategoryRepository.save(storeCategory);

	        return storeCategoryConverter.entityToDTO(savedStoreCategory);
	}
}
