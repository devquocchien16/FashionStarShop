package com.group4.fashionstarshop.controller;

import com.group4.fashionstarshop.dto.CategoryDTO;
import com.group4.fashionstarshop.dto.ProductDTO;
import com.group4.fashionstarshop.payload.SearchCategoryResponse;
import com.group4.fashionstarshop.service.CategoryService;
import com.group4.fashionstarshop.service.implement.CategoryServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/category/")
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
}
