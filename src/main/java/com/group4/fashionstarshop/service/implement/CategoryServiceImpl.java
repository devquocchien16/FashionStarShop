package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.converter.CategoryConverter;
import com.group4.fashionstarshop.dto.CategoryDTO;
import com.group4.fashionstarshop.dto.MainCategoryDTO;
import com.group4.fashionstarshop.dto.ParentCategoryDTO;
import com.group4.fashionstarshop.model.Category;
import com.group4.fashionstarshop.model.MainCategory;
import com.group4.fashionstarshop.model.ParentCategory;
import com.group4.fashionstarshop.repository.CategoryRepository;
import com.group4.fashionstarshop.repository.MainCategoryRepository;
import com.group4.fashionstarshop.repository.ParentCategoryRepository;
import com.group4.fashionstarshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CategoryConverter categoryConverter;
	@Autowired
	private MainCategoryRepository mainCategoryRepository;
	@Autowired
	private ParentCategoryRepository parentCategoryRepository;

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

	public List<CategoryDTO> findAllCategoryDtos() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDTO> categoriesDTO = categoryConverter.entitiesToDTOs(categories);
		return categoriesDTO;
	}

	@Override
	public List<CategoryDTO> findAllCategory() {
		// TODO Auto-generated method stub
		return categoryConverter.entitiesToDTOs(categoryRepository.findAll());
	}

	// theo hướng subcategory
	@Override
	public List<MainCategory> findAllMainCategories() {
		return mainCategoryRepository.findAll();
	}
	@Override
	public List<ParentCategory> findParentCategoriesByMainCategoryId(Long mainCategoryId) {
		MainCategory mainCategory = mainCategoryRepository.findById(mainCategoryId)
				.orElseThrow(() -> new EntityNotFoundException("MainCategory not found with id: " + mainCategoryId));
		return mainCategory.getParentCategories();
	}
	@Override
	public List<Category> findCategoriesByParentCategoryId(Long parentCategoryId) {
		ParentCategory parentCategory = parentCategoryRepository.findById(parentCategoryId).orElseThrow(
				() -> new EntityNotFoundException("ParentCategory not found with id: " + parentCategoryId));
		return parentCategory.getCategories();
	}

	//sub cate
	
	@Override
	public List<MainCategoryDTO> findAllMainCategory() {
		  List<MainCategory> mainCategories = mainCategoryRepository.findAll();		    
		    List<MainCategoryDTO> mainCategoryDTOs = mainCategories.stream()
		        .map(this::convertToMainCategoryDTO)
		        .collect(Collectors.toList());		        
		    return mainCategoryDTOs;
	}	
	
	@Override
	public List<ParentCategoryDTO> findAllParentCategory(Long main_cate_id) {
        MainCategory mainCategory = mainCategoryRepository.findById(main_cate_id).orElse(null);
        MainCategoryDTO mainCategoryDTO = convertToMainCategoryDTO(mainCategory);

        List<ParentCategory> parentCategories = parentCategoryRepository.findByMainCategory(mainCategory);

        List<ParentCategoryDTO> parentCategoryDTOs = new ArrayList<>();
        for (ParentCategory parentCategory : parentCategories) {
            ParentCategoryDTO parentCategoryDTO = convertToParentCategoryDTO(parentCategory);
            parentCategoryDTO.setMainCategoryDTO(mainCategoryDTO); // Thêm mainCategoryDTO vào parentCategoryDTO
            parentCategoryDTOs.add(parentCategoryDTO);
        }

        return parentCategoryDTOs;
    }
	
	 public List<CategoryDTO> getAllCategory(Long parent_cate_id) {
	        ParentCategory parentCategory = parentCategoryRepository.findById(parent_cate_id)
	                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
	        ParentCategoryDTO parentCategoryDTO = convertToParentCategoryDTO(parentCategory);

	        List<Category> categories = categoryRepository.findByParentCategory(parentCategory);

	        List<CategoryDTO> categoryDTOs = new ArrayList<>();
	        for (Category category : categories) {
	            CategoryDTO categoryDTO = categoryConverter.entityToDTO(category);
	            categoryDTO.setParentCategory(parentCategoryDTO); // Thêm parentCategoryDTO vào categoryDTO
	            categoryDTOs.add(categoryDTO);
	        }
	        return categoryDTOs;
	    }
	
	
	//convert for parent category
		private ParentCategoryDTO convertToParentCategoryDTO(ParentCategory parentCategory) {
		    ParentCategoryDTO parentCategoryDTO = new ParentCategoryDTO();	  
		    parentCategoryDTO.setId(parentCategory.getId());
		    parentCategoryDTO.setName(parentCategory.getName());   		   
		    return parentCategoryDTO;
		}
		public List<ParentCategoryDTO> entitiesToParentCategoryDTOs(List<ParentCategory> elements) {
		    return elements.stream()
		            .map(this::convertToParentCategoryDTO)
		            .collect(Collectors.toList());
		}
		//convert for main category
		private MainCategoryDTO convertToMainCategoryDTO(MainCategory mainCategory) {
		    MainCategoryDTO mainCategoryDTO = new MainCategoryDTO();
		    mainCategoryDTO.setId(mainCategory.getId());
		    mainCategoryDTO.setName(mainCategory.getName());
		    return mainCategoryDTO;
		}
		public List<MainCategoryDTO> entitiesToMainCategoryDTOs(List<MainCategory> elements) {
		    return elements.stream()
		            .map(this::convertToMainCategoryDTO)
		            .collect(Collectors.toList());
		}

}
