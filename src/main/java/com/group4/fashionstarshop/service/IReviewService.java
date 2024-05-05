package com.group4.fashionstarshop.service;


import com.group4.fashionstarshop.dto.ReviewDTO;
import com.group4.fashionstarshop.dto.SummaryDTO;

import java.util.List;

public interface IReviewService {
    public ReviewDTO getReviewById(Long id);
    public List<ReviewDTO> getReviewsByVariantId(Long variantId);
    public List<ReviewDTO> getReviewsByProductId(Long productId);
    public SummaryDTO getSummaryDtoByProductId(Long productId);
}
