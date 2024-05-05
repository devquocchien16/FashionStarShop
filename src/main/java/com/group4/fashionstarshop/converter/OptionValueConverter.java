package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.OptionValueDTO;
import com.group4.fashionstarshop.model.OptionValue;

import java.util.List;

public interface OptionValueConverter {
    List<OptionValueDTO> entitiesToDTOs(List<OptionValue> element);
    OptionValueDTO entityToDTO(OptionValue element);
    OptionValue dtoToEntity(OptionValueDTO element);
    List<OptionValue> dtosToEntities(List<OptionValueDTO> element);
}
