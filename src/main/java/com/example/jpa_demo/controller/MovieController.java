package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.entity.Favorite;
import com.example.jpa_demo.entity.Movie;
import com.example.jpa_demo.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieServiceImpl movieService;
    @GetMapping("/query/{id}")
    public BaseResponse<List<Movie>> queryById(@PathVariable Integer id){
        System.out.println(id);
        return BaseResponse.success(movieService.queryOverviewById(id));
    }

}
