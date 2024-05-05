package com.group4.fashionstarshop.repository;

import com.group4.fashionstarshop.model.Store;
import com.group4.fashionstarshop.model.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreCategoryRepository extends JpaRepository<StoreCategory, Long> {   
	 List<StoreCategory> findAllByParentStoreCategory(StoreCategory parentCategory);
	List<StoreCategory> findAllByStore(Store store);
    StoreCategory findByName(String name);
}
