package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.component.UserInfo;
import com.example.jpa_demo.entity.Comment;
import com.example.jpa_demo.service.CommentServiceImpl;
import com.example.jpa_demo.service.MovieServiceImpl;
import com.example.jpa_demo.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("")
    public BaseResponse<List<Comment>> getMovieComments(@RequestParam Integer movieId) {//获取某用户对某电影的评论
        List<Comment> comment = commentService.getCommentByMovieId(movieId);
        if (comment.isEmpty()) {
            return BaseResponse.error(10004, "该电影暂无评论");
        }
        return BaseResponse.success(comment);
    }

    @DeleteMapping("/{id}")
    public BaseResponse deleteByMovieId(@PathVariable(value = "id") Integer movieId) {
        String id = UserInfo.get("id");
        Optional<Comment> comment = commentService.getCommentsByUserIdAndMovieId(Integer.valueOf(id), movieId);
        if (!comment.isPresent()) {
            return BaseResponse.error(10004, "评论不存在");
        }
        commentService.deleteCommentByUserIdAndMovieId(Integer.valueOf(id), movieId);
        return BaseResponse.success("删除成功");
    }


    @PostMapping("")
    public BaseResponse commentAndRateOnMovie(@RequestBody CommentVO comment) {
        String id = UserInfo.get("id");
        if (comment.getComment() == null && comment.getRate() == null) {
            return BaseResponse.error(10005, "评论和评分不能同时为空");
        }
        if (movieService.findById(comment.getMovieId()).isEmpty()) {
            return BaseResponse.error(10001, "电影不存在");
        }
        Comment finalComment = Comment.builder()
                .userId(Integer.valueOf(id))
                .rate(comment.getRate())
                .comment(comment.getComment())
                .movieId(comment.getMovieId())
                .build();
        return BaseResponse.success(commentService.addOrModifyComment(finalComment));
    }

}
