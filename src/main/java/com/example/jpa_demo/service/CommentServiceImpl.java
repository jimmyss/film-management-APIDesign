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
    public List<Comment> getCommentsByUserIdAndId(Integer userId, Integer id) {
        List<Comment> comments=commentRepository.findCommentByUserIdAndId(userId, id);
        return comments;
    }

    @Override
    public List<Comment> getCommentsByUserIdAndMovieId(Integer userId, Integer movieId) {
        List<Comment> comments=commentRepository.findCommentByUserIdAndMovieId(userId, movieId);
        return comments;
    }

    @Override
    public boolean deleteCommentByUserIdAndId(Integer userId, Integer movieId) {
        List<Comment> comment=commentRepository.findCommentByUserIdAndId(userId, movieId);
        if(!comment.isEmpty()){
            return false;
        }
        commentRepository.delete(comment.get(0));
        return true;
    }

    @Override
    public List<Comment> addOrModifyComment(Comment comment) {
        List<Comment> getComment=commentRepository.findCommentByUserIdAndMovieId(comment.getUserId(), comment.getMovieId());
        if(getComment.isEmpty()){
            getComment.get(0).setComment(comment.getComment());
            getComment.get(0).setRate(comment.getRate());
            comment.setRate(getComment.get(0).getRate());
            comment.setComment(getComment.get(0).getComment());
            commentRepository.save(getComment.get(0));
        }else {
            commentRepository.save(comment);
        }
        List<Comment> comments=new ArrayList<>();
        comments.add(comment);
        return comments;
    }
}
