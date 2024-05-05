package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.ProductDTO;
import com.group4.fashionstarshop.model.Product;
import com.group4.fashionstarshop.request.ProductRequest;

import java.util.List;

public interface ProductConverter {
    List<ProductDTO> entitiesToDTOs(List<Product> element);
    ProductDTO entityToDTO(Product element);
    Product dtoToEntity(ProductRequest element);
    Product dtoToEntity(ProductDTO element);
}
