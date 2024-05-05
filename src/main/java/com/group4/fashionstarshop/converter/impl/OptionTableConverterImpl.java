package com.group4.fashionstarshop.converter.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.group4.fashionstarshop.converter.OptionTableConverter;
import com.group4.fashionstarshop.dto.OptionTableDTO;
import com.group4.fashionstarshop.model.OptionTable;

@Component
public class OptionTableConverterImpl implements OptionTableConverter {
	@Override
	public List<OptionTableDTO> entitiesToDTOs(List<OptionTable> element) {
		return element.stream().map(this::entityToDTO).collect(Collectors.toList());
	}

	@Override
	public OptionTableDTO entityToDTO(OptionTable element) {
		OptionTableDTO result = new OptionTableDTO();
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public OptionTable dtoToEntity(OptionTableDTO element) {
		OptionTable result = new OptionTable();
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public List<OptionTable> dtosToEntities(List<OptionTableDTO> element) {
		return element.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}

}
