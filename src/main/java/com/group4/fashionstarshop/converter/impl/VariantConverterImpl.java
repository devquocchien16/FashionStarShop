package com.group4.fashionstarshop.converter.impl;

import com.group4.fashionstarshop.converter.VariantConverter;
import com.group4.fashionstarshop.dto.VariantDTO;
import com.group4.fashionstarshop.model.Variant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class VariantConverterImpl implements VariantConverter {
	@Override
	public VariantDTO entityToDTO(Variant element) {
		VariantDTO result = new VariantDTO();
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public Variant dtoToEntity(VariantDTO element) {
		Variant result = new Variant();
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public List<Variant> dtosToEntities(List<VariantDTO> element) {
		return element.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}

	@Override
	public List<VariantDTO> entitiesToDTOs(List<Variant> element) {
		return element.stream().map(this::entityToDTO).collect(Collectors.toList());
	}
}