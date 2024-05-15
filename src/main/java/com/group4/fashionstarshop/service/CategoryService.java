package com.group4.fashionstarshop.service;

import com.group4.fashionstarshop.dto.CategoryDTO;
import com.group4.fashionstarshop.dto.MainCategoryDTO;
import com.group4.fashionstarshop.dto.ParentCategoryDTO;
import com.group4.fashionstarshop.model.Category;
import com.group4.fashionstarshop.model.MainCategory;
import com.group4.fashionstarshop.model.ParentCategory;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findByText(String text);

    CategoryDTO findCategory(Long categoryId);

	List<CategoryDTO> findAllCategory();

	List<CategoryDTO> findAllCategoryDtos();
	
	
	
	List<MainCategoryDTO> findAllMainCategory();

	List<ParentCategoryDTO> findAllParentCategory(Long main_cate_id);

	List<CategoryDTO> getAllCategory(Long parent_cate_id);

	List<Category> findCategoriesByParentCategoryId(Long parentCategoryId);

	List<ParentCategory> findParentCategoriesByMainCategoryId(Long mainCategoryId);

	List<MainCategory> findAllMainCategories();
	


	
}
