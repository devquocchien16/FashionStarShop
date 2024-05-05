package com.group4.fashionstarshop.controller;

import com.group4.fashionstarshop.dto.ReviewDTO;
import com.group4.fashionstarshop.request.ReviewRequest;
import com.group4.fashionstarshop.payload.ReviewCreateResponse;
import com.group4.fashionstarshop.payload.ReviewResponse;
import com.group4.fashionstarshop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/reviews")
public class ReviewController {
//    @Autowired
//    private ReviewService reviewService;
//
//    @GetMapping("/{variant_id}")
//    public ResponseEntity<ReviewResponse> getReviewsByVariantId(@PathVariable("variant_id") Long variantId){
//        ReviewResponse reviewResponse = new ReviewResponse();
//        reviewResponse.setReviewDTOList(reviewService.getReviewsByVariantId(variantId));
//        return new ResponseEntity<>(reviewResponse, HttpStatus.OK);
//
//    }
//
//    @GetMapping("/product/{product_id}")
//    public ResponseEntity<ReviewResponse> getReviewsByProductId(@PathVariable("product_id") Long productId){
//        ReviewResponse reviewResponse = new ReviewResponse();
//        reviewResponse.setReviewDTOList(reviewService.getReviewsByProductId(productId));
//        reviewResponse.setSummaryDto(reviewService.getSummaryDtoByProductId(productId));
//        return new ResponseEntity<>(reviewResponse, HttpStatus.OK);
//
//    }
//    @PostMapping("/{userId}/{variantId}")
//    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewRequest reviewRequest, @PathVariable("variantId") Long variantId, @PathVariable("userId") Long userId){
//        ReviewDTO response = reviewService.save(reviewRequest,variantId,userId);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//


}
