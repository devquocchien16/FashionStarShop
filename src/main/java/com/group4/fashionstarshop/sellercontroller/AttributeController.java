package com.group4.fashionstarshop.sellercontroller;

import com.group4.fashionstarshop.dto.ProductAttributeDTO;
import com.group4.fashionstarshop.model.Attribute;
import com.group4.fashionstarshop.request.ProductAttributeRequest;
import com.group4.fashionstarshop.payload.ProductAttributeResponse;
import com.group4.fashionstarshop.service.ProductAttributeService;

import java.util.List;

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
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/seller/attribute")
public class AttributeController {
	@Autowired
	private AttributeService attributeService;
    @Autowired
    private ProductAttributeService iProductAttributeService;
    @PostMapping("/{product_id}")
    public ResponseEntity<ProductAttributeResponse> createProductAttribute(@RequestBody ProductAttributeRequest productAttributeRequest, @PathVariable Long product_id){
        ProductAttributeResponse productAttributeResponse = new ProductAttributeResponse();
        List<ProductAttributeDTO> productAttributeDtoList = productAttributeRequest.getProductAttributeDtoList();
        List<Attribute> productAttributeList = iProductAttributeService.createProductAttribute(productAttributeDtoList, product_id);
        if(productAttributeList.size() > 0){
            productAttributeResponse.setMessage("successfully");
            return new ResponseEntity<>(productAttributeResponse, HttpStatus.OK);
        } else {
            productAttributeResponse.setMessage("fail to create");
            return new ResponseEntity<>(productAttributeResponse, HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/{product_id}/create")
    public ResponseEntity<AttributeDTO> createAttributes(@RequestBody AttributeRequest attributeRequest, @PathVariable(name = "product_id") Long product_id) {
        AttributeDTO createdAttribute = attributeService.createAttribute(attributeRequest, product_id);
        return new ResponseEntity<>(createdAttribute, HttpStatus.CREATED);
    }
    
    @PutMapping("/{attribute_id}/update")
    public ResponseEntity<AttributeDTO> updateAttribute(@RequestBody AttributeRequest attributeRequest, @PathVariable(name = "attribute_id") Long attribute_id) {
        AttributeDTO updatedAttribute = attributeService.updateAttribute(attributeRequest, attribute_id);       
        return new ResponseEntity<>(updatedAttribute, HttpStatus.CREATED);
    }
    @DeleteMapping("/{attribute_id}/delete")
    public ResponseEntity<?> deleteAttribute(@PathVariable(name = "attribute_id") Long attribute_id) {
    	 attributeService.deleteAttribute(attribute_id);       
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
