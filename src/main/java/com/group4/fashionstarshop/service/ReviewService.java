package com.group4.fashionstarshop.service;

import com.group4.fashionstarshop.dto.ReviewDTO;
import com.group4.fashionstarshop.dto.SummaryDTO;
import com.group4.fashionstarshop.request.ReviewRequest;
import com.group4.fashionstarshop.payload.ReviewCreateResponse;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface ReviewService {
    public ReviewDTO getReviewById(Long id);
    public List<ReviewDTO> getReviewsByVariantId(Long variantId);
    public List<ReviewDTO> getReviewsByProductId(Long productId);
    public SummaryDTO getSummaryDtoByProductId(Long productId);

    ReviewDTO save(ReviewRequest reviewRequest, Long variantId, Long userId);
}
