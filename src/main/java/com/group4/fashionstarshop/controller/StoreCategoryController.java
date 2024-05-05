package com.group4.fashionstarshop.controller;

import com.group4.fashionstarshop.dto.StoreCategoryDTO;
import com.group4.fashionstarshop.request.StoreCategoryResquest;
import com.group4.fashionstarshop.service.StoreCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/store-category")
public class StoreCategoryController {
    @Autowired
    private StoreCategoryService storeCategoryService;
    
    @GetMapping("/{store_id}/all")
    public ResponseEntity<List<StoreCategoryDTO>> getStoreCategoryByStoreId(@PathVariable("store_id") Long storeId) {
    	List<StoreCategoryDTO> storeCategoryDTOs = storeCategoryService.getStoreCategoryListByStoreId(storeId);
        return new ResponseEntity<>(storeCategoryDTOs, HttpStatus.OK);
    }
    
    @PostMapping("/{store_id}/create")
    public ResponseEntity<StoreCategoryDTO> createStoreCategoryWithStoreId(@RequestBody StoreCategoryResquest request, @PathVariable("store_id") Long storeId) {
    	System.out.println("for requset"+request.getCategory_id());
    	StoreCategoryDTO storeCategoryDTO = storeCategoryService.createStoreCategory(request, storeId);
        return new ResponseEntity<>(storeCategoryDTO, HttpStatus.OK);
    }  
    

    
}
