package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.component.UserInfo;
import com.example.jpa_demo.entity.Comment;
import com.example.jpa_demo.service.CommentServiceImpl;
import com.example.jpa_demo.service.MovieServiceImpl;
import com.example.jpa_demo.vo.CommentVO;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public BaseResponse<Page<Comment>> getMovieComments(
            @RequestParam Integer movieId,
            @RequestParam(required = false, defaultValue = "0")
            @Min(value = 0, message = "页码必须大于等于 0") Integer page,
            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 1, message = "页面大小必须大于0") Integer size
    ) {//获取某电影的评论
        if (movieService.findById(movieId).isEmpty()) {
            return BaseResponse.error(10001, "电影不存在");
        }
        Page<Comment> comment = commentService.getCommentByMovieId(movieId, page, size);
        return BaseResponse.success(comment);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<String> deleteByMovieId(@PathVariable(value = "id") Integer movieId) {
        String id = UserInfo.get("id");
        Optional<Comment> comment = commentService.getCommentsByUserIdAndMovieId(Integer.valueOf(id), movieId);
        if (comment.isEmpty()) {
            return BaseResponse.error(10004, "评论不存在");
        }
        commentService.deleteCommentByUserIdAndMovieId(Integer.valueOf(id), movieId);
        return BaseResponse.success("删除成功");
    }


    @PostMapping("")
    public BaseResponse<List<Comment>> commentAndRateOnMovie(@RequestBody CommentVO comment) {
        String id = UserInfo.get("id");
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
