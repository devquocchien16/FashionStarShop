package com.group4.fashionstarshop.converter.impl;

import com.group4.fashionstarshop.converter.OptionValueConverter;
import com.group4.fashionstarshop.dto.OptionValueDTO;
import com.group4.fashionstarshop.model.OptionValue;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OptionValueConverterImpl implements OptionValueConverter {

	@Override
	public List<OptionValueDTO> entitiesToDTOs(List<OptionValue> element) {
		return element.stream().map(this::entityToDTO).collect(Collectors.toList());
	}

	@Override
	public OptionValueDTO entityToDTO(OptionValue element) {
		OptionValueDTO result = new OptionValueDTO();
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public OptionValue dtoToEntity(OptionValueDTO element) {
		OptionValue result = new OptionValue();
		BeanUtils.copyProperties(element, result);
		return result;
	}

	@Override
	public List<OptionValue> dtosToEntities(List<OptionValueDTO> element) {
		return element.stream().map(this::dtoToEntity).collect(Collectors.toList());
	}
}
