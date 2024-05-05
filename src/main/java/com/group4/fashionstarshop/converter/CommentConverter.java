package com.group4.fashionstarshop.converter;

import com.group4.fashionstarshop.dto.CommentDTO;
import com.group4.fashionstarshop.model.Comment;

import java.util.List;

public interface CommentConverter {
    List<CommentDTO> entitiesToDTOs(List<Comment> element);
    CommentDTO entityToDTO(Comment element);
    Comment dtoToEntity(CommentDTO element);
}
