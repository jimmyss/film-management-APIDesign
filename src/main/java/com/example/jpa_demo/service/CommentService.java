package com.example.jpa_demo.service;

import java.util.List;
import java.util.Optional;

import com.example.jpa_demo.entity.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {
    Page<Comment> getCommentByMovieId(Integer movieId, Integer page, Integer size);
    Optional<Comment> getCommentsByUserIdAndMovieId(Integer userId, Integer movieId);

    boolean deleteCommentByUserIdAndMovieId(Integer userId, Integer movieId);

    List<Comment> addOrModifyComment(Comment comment);


}
