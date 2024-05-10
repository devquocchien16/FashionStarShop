package com.group4.fashionstarshop.service;

import com.group4.fashionstarshop.dto.ImageDTO;
import com.group4.fashionstarshop.model.Image;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ImageService {
    public List<ImageDTO> getImageByVariantId(Long variant_id);

    public List<Image> createImage(List<ImageDTO> imageDtos, Long variant_id);

	void deleteImageById(Long image_id);
}