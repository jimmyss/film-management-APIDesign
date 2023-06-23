package com.example.jpa_demo.service;

import java.util.List;
import java.util.Optional;

import com.example.jpa_demo.entity.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {
    Page<Comment> getCommentByMovieId(Integer movieId, Integer page, Integer size);
    List<Comment> getCommentsByUserIdAndId(Integer userId, Integer id);

    List<Comment> getCommentsByUserIdAndMovieId(Integer userId, Integer movieId);

    boolean deleteCommentByUserIdAndId(Integer userId, Integer id);

    List<Comment> addOrModifyComment(Comment comment);


}
