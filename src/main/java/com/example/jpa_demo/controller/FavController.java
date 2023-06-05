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
@RequestMapping("/fav")
public class FavController {
    @Autowired
    private FavoriteServiceImpl favoriteService;
    @GetMapping ("/list/{userId}")
    public BaseResponse<List<Favorite>> FavoriteList(@PathVariable  Integer userId){
        System.out.println(userId);
        return BaseResponse.success(favoriteService.listAll(userId));
    }

    @GetMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteFav(@PathVariable Integer id) {
        System.out.println(id);
        return BaseResponse.success(true);
    }

    @PostMapping("/add")
    public BaseResponse<Favorite> addFav(@Valid @RequestBody FavoriteVO favoriteVO) {
        Favorite favorite = new Favorite();
        favorite.setMovieId(favoriteVO.getMovieId());
        favorite.setUserId(favoriteVO.getUserId());
        System.out.println(favorite);
        return BaseResponse.success(favoriteService.add(favorite));
    }

    @GetMapping("/count/{userId}")
    public BaseResponse<Integer> countFav(@PathVariable Integer userId) {
        System.out.println(userId);
        return BaseResponse.success(favoriteService.countAll(userId));
    }
}
