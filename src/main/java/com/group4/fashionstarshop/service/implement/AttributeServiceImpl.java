package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.converter.AttributeConverter;
import com.group4.fashionstarshop.converter.impl.AttributeConverterImpl;
import com.group4.fashionstarshop.dto.AttributeDTO;
import com.group4.fashionstarshop.model.Product;
import com.group4.fashionstarshop.model.Attribute;
import com.group4.fashionstarshop.repository.AttributeRepository;
import com.group4.fashionstarshop.repository.ProductRepository;
import com.group4.fashionstarshop.request.AttributeRequest;
import com.group4.fashionstarshop.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;
    @Autowired
    private AttributeConverter attributeConverter;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<AttributeDTO> geAttributeByProductId(Long productId) {       
        List<Attribute> attributes = attributeRepository.findByProductId(productId);
        return attributeConverter.entitiesToDTOs(attributes);
    }

    @Override
    public AttributeDTO createAttribute(AttributeRequest request, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Attribute attribute = new Attribute();
        attribute.setName(request.getName());
        attribute.setValue(request.getValue());
        attribute.setProduct(product);
        Attribute savedAttribute = attributeRepository.save(attribute);
        return attributeConverter.entityToDTO(savedAttribute);
    }

    @Override
    public AttributeDTO updateAttribute(AttributeRequest attributeRequest, Long attributeId) {
        Attribute attribute = attributeRepository.findById(attributeId)
                .orElseThrow(() -> new EntityNotFoundException("Attribute not found"));

        attribute.setName(attributeRequest.getName());
        attribute.setValue(attributeRequest.getValue());

        Attribute updatedAttribute = attributeRepository.save(attribute);
        return attributeConverter.entityToDTO(updatedAttribute);
    }

    @Override
    public void deleteAttribute(Long attributeId) {
        attributeRepository.deleteById(attributeId);
    }


  

}
