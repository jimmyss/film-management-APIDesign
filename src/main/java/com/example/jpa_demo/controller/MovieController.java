package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.entity.Favorite;
import com.example.jpa_demo.entity.Movie;
import com.example.jpa_demo.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieServiceImpl movieService;
    @PostMapping("/query")
    public BaseResponse<List<Movie>> queryById(Integer id){
        System.out.println(id);
        return BaseResponse.success(movieService.queryOverviewById(id));
    }

}
