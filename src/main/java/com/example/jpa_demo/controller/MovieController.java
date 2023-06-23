package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.component.UserInfo;
import com.example.jpa_demo.entity.Movie;
import com.example.jpa_demo.service.MovieServiceImpl;
import com.example.jpa_demo.vo.MovieVO;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired
    private MovieServiceImpl movieService;

    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Integer id) {
        if (!UserInfo.get("role").equals("admin")) {
            return BaseResponse.error("权限不足");
        }
        movieService.deleteById(id);
        return BaseResponse.success("删除成功");
    }

    @GetMapping("/{id}")
    public BaseResponse<List<Movie>> queryById(@PathVariable Integer id){
        return BaseResponse.success(movieService.queryOverviewById(id));
    }


    @PostMapping("")
    public BaseResponse addMovie(@RequestBody @Validated MovieVO movieVO) {
        if (!UserInfo.get("role").equals("admin")) {
            return BaseResponse.error("权限不足");
        }
        Movie movie = new Movie();
        movie.setImdbId(movieVO.getImdbId());
        movie.setOriginalLanguage(movieVO.getOriginalLanguage());
        movie.setOverview(movieVO.getOverview());
        movie.setPopularity(movieVO.getPopularity());
        movie.setPosterPath(movieVO.getPosterPath());
        movie.setReleaseDate(movieVO.getReleaseDate());
        movie.setRevenue(movieVO.getRevenue());
        movie.setRuntime(movieVO.getRuntime());
        movie.setTitle(movieVO.getTitle());

        movieService.addMovie(movie);
        return BaseResponse.success("添加成功");
    }

    @GetMapping("")
    public BaseResponse<Page<Movie>> getMovies(@RequestParam(required = false, defaultValue = "") String search,
                                               @RequestParam(required = false, defaultValue = "0")
                                               @Min(value = 0, message = "Page number should be a positive number or zero") Integer page,
                                               @RequestParam(required = false, defaultValue = "10")
                                               @Min(value = 1, message = "Page size should be a positive number") Integer size) {
        if (search.equals("")) {
            return BaseResponse.success(movieService.queryAll(page, size));
        }
        return BaseResponse.success(movieService.queryByTitle(search, page, size));
    }
}
