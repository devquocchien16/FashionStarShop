package com.group4.fashionstarshop.controller;

import com.group4.fashionstarshop.dto.ProductDTO;
import com.group4.fashionstarshop.service.ProductAttributeService;
import com.group4.fashionstarshop.service.implement.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/products/attribute")
public class AttributeController {
	 @Autowired
    private ProductAttributeService attributeService;

    @GetMapping("/{product_id}")
    public List<ProductDTO> productDtoList(){
        return null;

    }
}
