package com.example.jpa_demo.service;

import java.util.List;
import java.util.Optional;

import com.example.jpa_demo.entity.Comment;

public interface CommentService {
    List<Comment> getCommentByUserIdAndMovieId(Integer userId, Integer movieId);

    boolean deleteCommentByUserIdAndMovieId(Integer userId, Integer movieId);

    List<Comment> addOrModifyComment(Comment comment);

    void getCommentNumByMovieId(Integer movieId);

    List<Comment> getCommentsByUserId(Integer userId);
}
