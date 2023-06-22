package com.example.jpa_demo.service;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.entity.Comment;
import com.example.jpa_demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @projectName: apidesign
 * @package: com.example.jpa_demo.service
 * @className: CommentServiceImlp
 * @author: Dushimao
 * @description: TODO
 * @date: 2023/6/22 14:04
 * @version: 1.0
 */
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public List<Comment> getCommentByUserIdAndMovieId(Integer userId, Integer movieId) {
        Optional<Comment> comment=commentRepository.findCommentByUserIdAndMovieId(userId, movieId);
        if(!comment.isPresent()){
            //如果评论不存在
            return new ArrayList<>();
        }
        List<Comment> finalComment=new ArrayList<>();
        finalComment.add(comment.get());
        return finalComment;
    }

    @Override
    public boolean deleteCommentByUserIdAndMovieId(Integer userId, Integer movieId) {
        Optional<Comment> comment=commentRepository.findCommentByUserIdAndMovieId(userId, movieId);
        if(!comment.isPresent()){
            return false;
        }
        commentRepository.delete(comment.get());
        return true;
    }

    @Override
    public List<Comment> addOrModifyComment(Comment comment) {
        commentRepository.save(comment);
        List<Comment> comments=new ArrayList<>();
        comments.add(comment);
        return comments;
    }

    @Override
    public void getCommentNumByMovieId(Integer movieId) {

    }
}
