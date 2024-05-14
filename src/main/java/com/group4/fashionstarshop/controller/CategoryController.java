package com.group4.fashionstarshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group4.fashionstarshop.dto.CategoryDTO;
import com.group4.fashionstarshop.dto.MainCategoryDTO;
import com.group4.fashionstarshop.dto.ParentCategoryDTO;
import com.group4.fashionstarshop.payload.SearchCategoryResponse;
import com.group4.fashionstarshop.service.CategoryService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;    
  
    @GetMapping("/search")
    public ResponseEntity<SearchCategoryResponse> searchCategories(@RequestParam("query") String query){
        SearchCategoryResponse searchCategoryResponse = new SearchCategoryResponse();
        List<CategoryDTO> categoryDtoList = categoryService.findByText(query);
        searchCategoryResponse.setCategoryDtoList(categoryDtoList);
        return ResponseEntity.ok(searchCategoryResponse);
    }
    @GetMapping("/{category_id}")
    public ResponseEntity<CategoryDTO> getStore(@PathVariable("category_id") Long category_id) {
        CategoryDTO CategoryDto = categoryService.findCategory(category_id);
        return new ResponseEntity<>(CategoryDto, HttpStatus.OK);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getStore() {
    	List<CategoryDTO> categoryDtos = categoryService.findAllCategory();
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }
    
    //theo huong subcategory
    
    @GetMapping("/main-cate/all")
    public ResponseEntity<List<MainCategoryDTO>> getMainCategory() {
    	List<MainCategoryDTO> mainCategoryDTOs = categoryService.findAllMainCategory();
        return new ResponseEntity<>(mainCategoryDTOs, HttpStatus.OK);
    }
    
    
    @GetMapping("/main-cate/{main_cate_id}")
    public ResponseEntity<List<ParentCategoryDTO>> getParentCategory(@PathVariable("main_cate_id") Long main_cate_id) {
    	List<ParentCategoryDTO> parentCategoryDTOs = categoryService.findAllParentCategory(main_cate_id);
        return new ResponseEntity<>(parentCategoryDTOs, HttpStatus.OK);
    }
    
    @GetMapping("/main-cate/parent-cate/{parent_cate_id}")
    public ResponseEntity<List<CategoryDTO>> getCategory(@PathVariable("parent_cate_id") Long parent_cate_id) {
    	List<CategoryDTO> categoryDTOs = categoryService.getAllCategory(parent_cate_id);
        return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
    }
}
