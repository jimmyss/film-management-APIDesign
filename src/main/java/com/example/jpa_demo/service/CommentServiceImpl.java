package com.example.jpa_demo.service;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.entity.Comment;
import com.example.jpa_demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
    public Page<Comment> getCommentByMovieId(Integer movieId, Integer page, Integer size) {
        Pageable pageable= PageRequest.of(page, size);
        Page<Comment> comments=commentRepository.findCommentsByMovieId(movieId, pageable);
        return comments;
    }

    @Override
    public Optional<Comment> getCommentsByUserIdAndMovieId(Integer userId, Integer movieId) {
        Optional<Comment> comments=commentRepository.findCommentByUserIdAndMovieId(userId, movieId);
        return comments;
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
        Optional<Comment> getComment=commentRepository.findCommentByUserIdAndMovieId(comment.getUserId(), comment.getMovieId());
        if(getComment.isPresent()){
            getComment.get().setComment(comment.getComment());
            getComment.get().setRate(comment.getRate());
            comment.setRate(getComment.get().getRate());
            comment.setComment(getComment.get().getComment());
            commentRepository.save(getComment.get());
        }else {
            commentRepository.save(comment);
        }
        List<Comment> comments=new ArrayList<>();
        comments.add(comment);
        return comments;
    }
}
