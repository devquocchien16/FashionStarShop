package com.group4.fashionstarshop.service.implement;

import com.group4.fashionstarshop.converter.CommentConverter;
import com.group4.fashionstarshop.dto.CommentDTO;
import com.group4.fashionstarshop.model.Comment;
import com.group4.fashionstarshop.repository.CommentRepository;
import com.group4.fashionstarshop.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;


    public CommentServiceImpl(CommentRepository commentRepository, CommentConverter commentConverter) {
        this.commentRepository = commentRepository;
        this.commentConverter = commentConverter;
    }

    @Override
    public List<CommentDTO> getCommentsByReviewId(Long reviewId) {
        List<Comment> commentList = commentRepository.getCommentsByReviewId(reviewId);
        List<CommentDTO> commentDtoList = commentConverter.entitiesToDTOs(commentList);
        return commentDtoList;
    }
}
