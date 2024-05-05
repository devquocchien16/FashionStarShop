package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.converter.CategoryConverter;
import com.group4.fashionstarshop.dto.CategoryDTO;
import com.group4.fashionstarshop.model.Category;
import com.group4.fashionstarshop.repository.CategoryRepository;
import com.group4.fashionstarshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryConverter categoryConverter;
    @Override
    public List<CategoryDTO> findByText(String text) {
        List<Category> categories = categoryRepository.findByNameContaining(text);
        List<CategoryDTO> categoryDtoList = categoryConverter.entitiesToDTOs(categories);
        return categoryDtoList;
    }

    @Override
    public CategoryDTO findCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("category not found"));
        CategoryDTO categoryDto = categoryConverter.entityToDTO(category);
        return categoryDto;
    }
    
    public List<CategoryDTO> findAllCategoryDtos(){
    	List<Category> categories = categoryRepository.findAll();
    	List<CategoryDTO> categoriesDTO = categoryConverter.entitiesToDTOs(categories);
    	return categoriesDTO;
    }

	@Override
	public List<CategoryDTO> findAllCategory() {
		// TODO Auto-generated method stub
		return categoryConverter.entitiesToDTOs(categoryRepository.findAll());
	}

}
