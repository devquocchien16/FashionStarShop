package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.ImageDTO;
import com.group4.fashionstarshop.model.Image;

import java.util.List;

public interface ImageConverter {
    ImageDTO entityToDTO(Image element);

    List<ImageDTO> entitiesToDTOs(List<Image> element);

    Image dtoToEntity(ImageDTO element);
    List<Image> dtosToEntities(List<ImageDTO> element);

}
