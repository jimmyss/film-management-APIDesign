package com.example.jpa_demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jpa_demo.entity.Comment;

import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Optional<Comment> findCommentByUserIdAndMovieId(Integer userId, Integer movieId);
    Page<Comment> findCommentsByMovieId(Integer movieId, Pageable pageable);

}
