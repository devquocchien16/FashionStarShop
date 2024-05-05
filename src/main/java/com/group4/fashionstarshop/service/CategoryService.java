package com.group4.fashionstarshop.service;

import com.group4.fashionstarshop.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findByText(String text);

    CategoryDTO findCategory(Long categoryId);

	List<CategoryDTO> findAllCategory();

	List<CategoryDTO> findAllCategoryDtos();
}
