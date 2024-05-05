package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.converter.ProductAttributeConverter;
import com.group4.fashionstarshop.converter.impl.ProductAttributeConverterImpl;
import com.group4.fashionstarshop.dto.ProductAttributeDTO;
import com.group4.fashionstarshop.model.Product;
import com.group4.fashionstarshop.model.Attribute;
import com.group4.fashionstarshop.repository.ProductAttributeRepository;
import com.group4.fashionstarshop.repository.ProductRepository;
import com.group4.fashionstarshop.service.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {

    @Autowired
    private ProductAttributeRepository productAttributeRepository;
    @Autowired
    private ProductAttributeConverter productAttributeConverter;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductAttributeDTO> getProductAttributeByProductId(Long product_id) {
        List<Attribute> productAttributeList = productAttributeRepository.findByProductId(product_id);
        return productAttributeConverter.entitiesToDTOs(productAttributeList);
    }

    @Override
    public List<Attribute> createProductAttribute(List<ProductAttributeDTO> productAttributeDtoList, Long product_Id) {
        Product product = productRepository.findById(product_Id)
                .orElseThrow(()-> new EntityNotFoundException("Product not found"));
        if(product != null){
            for(ProductAttributeDTO element : productAttributeDtoList){
                Attribute attribute = new Attribute();
                attribute.setName(element.getName());
                attribute.setValue(element.getValue());
                attribute.setProduct(product);
                productAttributeRepository.save(attribute);
            }
        }
        List<Attribute> productAttributeList = product.getAttributes();
        return productAttributeList;
    }

}
