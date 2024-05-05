package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.CategoryDTO;
import com.group4.fashionstarshop.model.Category;

import java.util.List;

public interface CategoryConverter {
    List<CategoryDTO> entitiesToDTOs(List<Category> element);
    CategoryDTO entityToDTO(Category element);
    Category dtoToEntity(CategoryDTO element);
}
