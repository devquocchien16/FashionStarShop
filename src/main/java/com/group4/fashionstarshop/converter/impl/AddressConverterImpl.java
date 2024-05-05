package com.group4.fashionstarshop.converter.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.group4.fashionstarshop.converter.AddressConverter;
import com.group4.fashionstarshop.dto.AddressDTO;
import com.group4.fashionstarshop.model.Address;

@Component
public class AddressConverterImpl implements AddressConverter {
	@Override
	public AddressDTO entityToDTO(Address element) {
		AddressDTO result = new AddressDTO();
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public Address dtoToEntity(AddressDTO element) {
		Address result = new Address();
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public List<Address> dtosToEntities(List<AddressDTO> element) {
		return element.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}

	@Override
	public List<AddressDTO> entitiesToDTOs(List<Address> element) {
		return element.stream().map(this::entityToDTO).collect(Collectors.toList());
	}
}
