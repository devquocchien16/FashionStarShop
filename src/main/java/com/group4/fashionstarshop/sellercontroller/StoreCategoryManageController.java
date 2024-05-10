package com.group4.fashionstarshop.sellercontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group4.fashionstarshop.dto.CategoryDTO;
import com.group4.fashionstarshop.dto.StoreCategoryDTO;
import com.group4.fashionstarshop.request.StoreCategoryResquest;
import com.group4.fashionstarshop.service.CategoryService;
import com.group4.fashionstarshop.service.StoreCategoryService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/seller/store-category")
public class StoreCategoryManageController {
	@Autowired
	private StoreCategoryService storeCategoryService;  
	@Autowired
	private CategoryService categoryService;  
    
    @PostMapping("/{store_id}/create")
    public ResponseEntity<StoreCategoryDTO> createAttribute(@RequestBody StoreCategoryResquest request, @PathVariable(name = "store_id") Long store_id) {
    	StoreCategoryDTO createdStoreCategoryDTO = storeCategoryService.createStoreCategory(request, store_id);
        return new ResponseEntity<>(createdStoreCategoryDTO, HttpStatus.CREATED);
    }   
    @GetMapping("/main-category")
    public ResponseEntity<List<CategoryDTO>> getMainCategory() {
    	List<CategoryDTO> categories = categoryService.findAllCategoryDtos();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }   
}
