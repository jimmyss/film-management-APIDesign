package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.component.UserInfo;
import com.example.jpa_demo.entity.Favorite;
import com.example.jpa_demo.entity.Movie;
import com.example.jpa_demo.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired
    private MovieServiceImpl movieService;
    @GetMapping("/{id}")
    public BaseResponse<List<Movie>> queryById(@PathVariable Integer id){
        System.out.println(id);
        return BaseResponse.success(movieService.queryOverviewById(id));
    }

    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Integer id){
        if(!UserInfo.get("role").equals("admin")){
            return BaseResponse.error("权限不足");
        }
        movieService.deleteById(id);
        return BaseResponse.success("删除成功");
    }

    @PostMapping("")
    public BaseResponse addMovie(@RequestBody Movie movie){
        if(!UserInfo.get("role").equals("admin")){
            return BaseResponse.error("权限不足");
        }
        movieService.addMovie(movie);
        return BaseResponse.success("添加成功");
    }

    @GetMapping("")
    public BaseResponse<List<Movie>> queryBySearch(@RequestParam String search){
        return BaseResponse.success(movieService.queryByTitle(search));
    }
}
