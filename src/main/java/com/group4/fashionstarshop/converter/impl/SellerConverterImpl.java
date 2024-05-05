package com.group4.fashionstarshop.converter.impl;

import com.group4.fashionstarshop.converter.AddressConverter;
import com.group4.fashionstarshop.converter.SellerConverter;
import com.group4.fashionstarshop.dto.AddressDTO;
import com.group4.fashionstarshop.dto.SellerDTO;
import com.group4.fashionstarshop.dto.SellerLoginDTO;
import com.group4.fashionstarshop.model.Seller;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class SellerConverterImpl implements SellerConverter {
	@Autowired
	 private AddressConverter addressConverter;
	
    @Override
    public List<SellerDTO> entitiesToDTOs(List<Seller> element) {
        return element.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SellerDTO entityToDTO(Seller element) {
        SellerDTO result = new SellerDTO();
        BeanUtils.copyProperties(element, result);       
        return result;
    }

    @Override
    public Seller dtoToEntity(SellerDTO element) {
        Seller result = new Seller();
        BeanUtils.copyProperties(element, result);
        return result;
    }

    @Override
    public SellerDTO convertEntityToDTO(Seller seller) {
        SellerDTO result = new SellerDTO();
        BeanUtils.copyProperties(seller, result);
        return result;
    }
}
