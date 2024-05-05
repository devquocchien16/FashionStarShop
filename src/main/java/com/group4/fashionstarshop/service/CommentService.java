package com.group4.fashionstarshop.service;

import com.group4.fashionstarshop.dto.CommentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    public List<CommentDTO> getCommentsByReviewId(Long reviewId);
}
