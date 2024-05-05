package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.OptionTableDTO;
import com.group4.fashionstarshop.model.OptionTable;

import java.util.List;

public interface OptionTableConverter {
    OptionTableDTO entityToDTO(OptionTable element);

    List<OptionTableDTO> entitiesToDTOs(List<OptionTable> element);

    OptionTable dtoToEntity(OptionTableDTO element);
     List<OptionTable> dtosToEntities(List<OptionTableDTO> element);
}
