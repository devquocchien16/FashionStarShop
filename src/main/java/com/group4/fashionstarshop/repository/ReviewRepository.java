package com.group4.fashionstarshop.repository;

import com.group4.fashionstarshop.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.Max;
import java.util.List;

@Repository

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewsByVariantId(Long variantId);

    List<Review> findByVariant_Id(Long id);

}
