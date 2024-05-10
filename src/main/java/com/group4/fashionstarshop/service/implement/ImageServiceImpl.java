package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.converter.impl.ImageConverterImpl;
import com.group4.fashionstarshop.dto.ImageDTO;
import com.group4.fashionstarshop.model.Image;
import com.group4.fashionstarshop.model.Variant;
import com.group4.fashionstarshop.repository.ImageRepository;
import com.group4.fashionstarshop.repository.VariantRepository;
import com.group4.fashionstarshop.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ImageConverterImpl imageConverterImpl;
    private final VariantRepository variantRepository;


    @Autowired
    public ImageServiceImpl(
            ImageRepository imageRepository,
            ImageConverterImpl imageConverterImpl,
            VariantRepository variantRepository
    ) {
        this.imageRepository = imageRepository;
        this.imageConverterImpl = imageConverterImpl;
        this.variantRepository = variantRepository;
    }

    @Override
    public List<ImageDTO> getImageByVariantId(Long variant_id) {
        List<Image> images = imageRepository.findImagesByVariant_Id(variant_id);
        return imageConverterImpl.entitiesToDTOs(images);
    }
    @Override
    public List<Image> createImage(List<ImageDTO> imageDtoList, Long variant_id) {
        Variant variant = variantRepository.findById(variant_id).orElse(null);
        List<Image> images= imageConverterImpl.dtosToEntities(imageDtoList);
        for(Image element : images){
            element.setVariant(variant);
            imageRepository.save(element);
        }
        assert variant != null;
        return variant.getImages();
    }
    @Override
    public void deleteImageById(Long image_id) {
        imageRepository.deleteById(image_id);;
        
    }
    
}
