package com.group4.fashionstarshop.sellercontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group4.fashionstarshop.dto.ProductDTO;
import com.group4.fashionstarshop.request.ProductRequest;
import com.group4.fashionstarshop.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/seller/product")
public class ProductManageController {
	
	@Autowired
    private ProductService productService;  
	
    @PostMapping("{storeId}/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductRequest productRequest, @PathVariable(name = "storeId") Long storeId) {
    	ProductDTO createdProduct = productService.createProduct(productRequest, storeId);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }  
    
  	@PutMapping("/update/{product_id}")
  	public ResponseEntity<ProductDTO> updateProduct(@PathVariable(name = "product_id") Long product_id , @RequestBody ProductRequest request) {
  		ProductDTO updatedProduct = productService.updateProduct(product_id, request);
  		 return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
  	}  
	@GetMapping("/details/{product_id}")
  	public ResponseEntity<ProductDTO> getProductById(@PathVariable(name = "product_id") Long product_id) {
  		ProductDTO productDTO = productService.getProductById(product_id);
  		 return new ResponseEntity<>(productDTO, HttpStatus.OK);
  	}  

}
