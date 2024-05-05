package com.group4.fashionstarshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group4.fashionstarshop.model.Comment;

import java.util.List;

@Repository

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> getCommentsByReviewId(Long id);
}
