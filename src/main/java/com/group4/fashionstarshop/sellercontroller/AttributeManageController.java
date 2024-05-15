package com.group4.fashionstarshop.sellercontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group4.fashionstarshop.dto.AttributeDTO;
import com.group4.fashionstarshop.request.AttributeRequest;
import com.group4.fashionstarshop.service.AttributeService;

@RestController
@RequestMapping("/api/seller/attribute")
public class AttributeManageController {
	@Autowired
	private AttributeService attributeService;  
    
    @PostMapping("/{product_id}/create")
    public ResponseEntity<AttributeDTO> createAttribute(@RequestBody AttributeRequest attributeRequest, @PathVariable(name = "product_id") Long product_id) {
        AttributeDTO createdAttribute = attributeService.createAttribute(attributeRequest, product_id);
        return new ResponseEntity<>(createdAttribute, HttpStatus.CREATED);
    }
    
    @PutMapping("/update/{attribute_id}")
    public ResponseEntity<AttributeDTO> updateAttribute(@RequestBody AttributeRequest attributeRequest, @PathVariable(name = "attribute_id") Long attribute_id) {
        AttributeDTO updatedAttribute = attributeService.updateAttribute(attributeRequest, attribute_id);       
        return new ResponseEntity<>(updatedAttribute, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{attribute_id}")
    public ResponseEntity<?> deleteAttribute(@PathVariable(name = "attribute_id") Long attribute_id) {
    	 attributeService.deleteAttribute(attribute_id);       
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
