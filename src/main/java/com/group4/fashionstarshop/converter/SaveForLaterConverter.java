package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.SaveForLaterDTO;
import com.group4.fashionstarshop.model.SaveForLater;

import java.util.List;

public interface SaveForLaterConverter {
    SaveForLater convertDtoToEntity(SaveForLaterDTO saveForLaterDto);
    SaveForLaterDTO convertEntityToDto(SaveForLater saveForLater);
    List<SaveForLaterDTO> convertEntitiesToDtos(List<SaveForLater> saveForLaters);
    List<SaveForLater> convertDtoToEntities(List<SaveForLaterDTO> cartLineDtos);
}
