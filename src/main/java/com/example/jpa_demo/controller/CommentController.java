package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.component.UserInfo;
import com.example.jpa_demo.entity.User;
import com.example.jpa_demo.service.CommentServiceImpl;
import com.example.jpa_demo.entity.Comment;
import com.example.jpa_demo.service.MovieServiceImpl;
import com.example.jpa_demo.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: apidesign
 * @package: com.example.jpa_demo.controller
 * @className: CommentController
 * @author: Dushimao
 * @description: TODO
 * @date: 2023/6/22 14:03
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;
    @Autowired
    private MovieServiceImpl movieService;

    @GetMapping("/{movie_id}")
    public BaseResponse<List<Comment>> getComment(
            @RequestHeader("Authorization") String tokenBearer,
            @PathVariable(value = "movie_id") Integer movieId
    ){
        String id= UserInfo.get("id");
        List<Comment> comment=commentService.getCommentByUserIdAndMovieId(Integer.valueOf(id), movieId);
        if(comment.isEmpty()){
            return BaseResponse.error(10004, "评论不存在");
        }
        return BaseResponse.success(comment);
    }

    @DeleteMapping("/{movie_id}")
    public BaseResponse deleteByMovieId(
            @RequestHeader("Authorization") String tokenBearer,
            @PathVariable(value = "movie_id") Integer movieId)
    {
        String id= UserInfo.get("id");
        List<Comment>comment=commentService.getCommentByUserIdAndMovieId(Integer.valueOf(id), movieId);
        if(comment.isEmpty()){
            return BaseResponse.error(10004, "评论不存在");
        }
        commentService.deleteCommentByUserIdAndMovieId(Integer.valueOf(id), movieId);
        return BaseResponse.success("删除成功");
    }

    @GetMapping("/all")
    public BaseResponse<List<Comment>> getComment(
            @RequestHeader("Authorization") String tokenBearer
    ){
        String id= UserInfo.get("id");
        List<Comment> comment=commentService.getCommentsByUserId(Integer.valueOf(id));
        if(comment.isEmpty()){
            return BaseResponse.error(10006, "该用户没有评论任何电影");
        }
        return BaseResponse.success(comment);
    }

    @PostMapping("")
    public BaseResponse commentAndRateOnMovie(
            @RequestBody CommentVO comment,
            @RequestHeader("Authorization") String tokenBearer)
    {
        String id=UserInfo.get("id");
        if(comment.getComment()==null && comment.getRate()==null){
            return BaseResponse.error(10005, "评论和评分不能同时为空");
        }
        if(movieService.findById(comment.getMovieId()).isEmpty()){
            return BaseResponse.error(10001, "电影不存在");
        }
        Comment finalComment=Comment.builder()
                .userId(Integer.valueOf(id))
                .rate(comment.getRate())
                .comment(comment.getComment())
                .movieId(comment.getMovieId())
                .build();
        return BaseResponse.success(commentService.addOrModifyComment(finalComment));
    }


}
