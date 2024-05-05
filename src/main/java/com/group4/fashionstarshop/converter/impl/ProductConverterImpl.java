package com.group4.fashionstarshop.converter.impl;

import com.group4.fashionstarshop.converter.ProductConverter;
import com.group4.fashionstarshop.dto.ProductDTO;
import com.group4.fashionstarshop.model.Product;
import com.group4.fashionstarshop.request.ProductRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverterImpl implements ProductConverter {
	@Override
	public List<ProductDTO> entitiesToDTOs(List<Product> element) {
		return element.stream().map(this::entityToDTO).collect(Collectors.toList());
	}

	@Override
	public ProductDTO entityToDTO(Product element) {
		ProductDTO result = new ProductDTO();
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public Product dtoToEntity(ProductRequest element) {
		Product result = new Product();
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public Product dtoToEntity(ProductDTO element) {
		Product result = new Product();
		BeanUtils.copyProperties(element, result);
		return result;
	}
}
