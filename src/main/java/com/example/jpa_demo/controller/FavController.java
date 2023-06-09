package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.entity.Favorite;
import com.example.jpa_demo.service.FavoriteServiceImpl;
import com.example.jpa_demo.vo.FavoriteVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
@RequestMapping("/api/favorites")
public class FavController {
    @Autowired
    private FavoriteServiceImpl favoriteService;
    @GetMapping ("/list/{user_id}")
    public BaseResponse<List<Favorite>> FavoriteList(@PathVariable(value = "user_id")  Integer userId){
        System.out.println(userId);
        return BaseResponse.success(favoriteService.listAll(userId));
    }

    @DeleteMapping("/del/{id}")
    public BaseResponse<Boolean> deleteFav(@PathVariable Integer id) {
        System.out.println(id);
        return BaseResponse.success(true);
    }

    @PostMapping("")
    public BaseResponse<Favorite> addFav(@Valid @RequestBody FavoriteVO favoriteVO) {
        Favorite favorite = new Favorite();
        favorite.setMovieId(favoriteVO.getMovieId());
        favorite.setUserId(favoriteVO.getUserId());
        System.out.println(favorite);
        return BaseResponse.success(favoriteService.add(favorite));
    }

    @GetMapping("/count/{movie_id}")
    public BaseResponse<Integer> countFav(@PathVariable(value = "movie_id") Integer movieId) {
        System.out.println(movieId);
        return BaseResponse.success(favoriteService.countAll(movieId));
    }
}
