package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.converter.OptionValueConverter;
import com.group4.fashionstarshop.converter.impl.OptionValueConverterImpl;
import com.group4.fashionstarshop.dto.OptionTableDTO;
import com.group4.fashionstarshop.dto.OptionValueDTO;
import com.group4.fashionstarshop.model.OptionTable;
import com.group4.fashionstarshop.model.OptionValue;
import com.group4.fashionstarshop.model.Product;
import com.group4.fashionstarshop.model.Variant;
import com.group4.fashionstarshop.model.VariantOptionValue;
import com.group4.fashionstarshop.repository.OptionTableRepository;
import com.group4.fashionstarshop.repository.OptionValueRepository;
import com.group4.fashionstarshop.repository.ProductRepository;
import com.group4.fashionstarshop.repository.VariantOptionValueRepository;
import com.group4.fashionstarshop.repository.VariantRepository;
import com.group4.fashionstarshop.request.OptionRequest;
import com.group4.fashionstarshop.request.OptionValueRequest;
import com.group4.fashionstarshop.service.OptionValueService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OptionValueServiceImpl implements OptionValueService {
	@Autowired
	private VariantRepository variantRepository;
	@Autowired
	private OptionValueRepository optionValueRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OptionTableRepository optionTableRepository;
	@Autowired
	private OptionValueConverter optionValueConverter;
	@Autowired
	private VariantOptionValueRepository variantOptionValueRepository;

	@Override
	public List<OptionValueDTO> getOptionValuesByVariantId(Long variant_id) {
		OptionValueDTO optionValueDTO = new OptionValueDTO();
		List<OptionValueDTO> optionValueDTOList = new ArrayList<>();
		Variant variant = variantRepository.findById(variant_id).orElse(null);
		List<VariantOptionValue> variantOptionValues = variantOptionValueRepository.findByVariant(variant);
		for (var item : variantOptionValues) {
			optionValueDTO = optionValueConverter.entityToDTO(item.getOption_value());
			optionValueDTOList.add(optionValueDTO);
		}
		return optionValueDTOList;
	}

	@Override
	public OptionValueDTO createOptionValue(OptionValueRequest optionValueRequest, Long option_id) {
		OptionTable option = optionTableRepository.findById(option_id)
				.orElseThrow(() -> new EntityNotFoundException("OptionTable not found"));
		OptionValue optionValue = new OptionValue();
		optionValue.setOptionTable(option);
		optionValue.setValue(optionValueRequest.getValue());
		// Save all OptionValues
		OptionValue savedOptionValue = optionValueRepository.save(optionValue);
		return optionValueConverter.entityToDTO(savedOptionValue);

	}

	@Override
	public List<OptionValueDTO> getOptionValuesByOptionId(Long option_id) {
		OptionTable option = optionTableRepository.findById(option_id)
				.orElseThrow(() -> new EntityNotFoundException("OptionTable not found"));

		List<OptionValue> optionValues = optionValueRepository.findByOptionTable(option);

		return optionValueConverter.entitiesToDTOs(optionValues);
	}

	@Override
	public OptionValueDTO updateOptionValue(OptionValueRequest request, Long option_value_id) {
		// Find the option by its ID
		OptionValue optionValue = optionValueRepository.findById(option_value_id)
				.orElseThrow(() -> new EntityNotFoundException("Option nValue not found"));
		// Update the option name
		optionValue.setValue(request.getValue());
		// Save the updated option
		optionValueRepository.save(optionValue);
		// Convert and return the updated option DTO
		return optionValueConverter.entityToDTO(optionValue);
	}

}
