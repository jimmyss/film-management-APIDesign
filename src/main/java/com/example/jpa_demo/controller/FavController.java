package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.entity.Favorite;
import com.example.jpa_demo.service.FavoriteService;
import com.example.jpa_demo.service.FavoriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/fav")
public class FavController {
    @Autowired
    private FavoriteServiceImpl favoriteService;
    @PostMapping("/list")
    public BaseResponse<List<Favorite>> FavoriteList(Integer userId){
        System.out.println(userId);
        return BaseResponse.success(favoriteService.listAll(userId));
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteFav(Integer id) {
        System.out.println(id);
        return BaseResponse.success(true);
    }

    @PostMapping("/add")
    public BaseResponse<Favorite> addFav(Favorite favorite) {
        System.out.println(favorite);
        return BaseResponse.success(favoriteService.add(favorite));
    }

    @PostMapping("/count")
    public BaseResponse<Integer> countFav(Integer userId) {
        System.out.println(userId);
        return BaseResponse.success(favoriteService.countAll(userId));
    }
}
