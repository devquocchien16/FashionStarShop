package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.VariantDTO;
import com.group4.fashionstarshop.model.Variant;

import java.util.List;

public interface VariantConverter {
	VariantDTO entityToDTO(Variant element);

	Variant dtoToEntity(VariantDTO element);

	List<Variant> dtosToEntities(List<VariantDTO> element);

	List<VariantDTO> entitiesToDTOs(List<Variant> element);
}
