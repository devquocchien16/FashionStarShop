package com.group4.fashionstarshop.sellercontroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group4.fashionstarshop.dto.ProductDTO;
import com.group4.fashionstarshop.request.ProductRequest;
import com.group4.fashionstarshop.service.ImageService;
import com.group4.fashionstarshop.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/seller/image")
public class ImageManageController {
	
	@Autowired
    private ImageService imageService;  
	  
    
  	@DeleteMapping("/delete/{image_id}")
  	public ResponseEntity<?> deleteImage(@PathVariable(name = "image_id") Long image_id ) {
  		imageService.deleteImageById(image_id);
  		 return new ResponseEntity<>(HttpStatus.OK);
  	}  

}
