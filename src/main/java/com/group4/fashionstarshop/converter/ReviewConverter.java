package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.ReviewDTO;
import com.group4.fashionstarshop.model.Review;

import java.util.List;

public interface ReviewConverter {
    List<ReviewDTO> entitiesToDTOs(List<Review> element);
    ReviewDTO entityToDTO(Review element);
    Review dtoToEntity(ReviewDTO element);
}
