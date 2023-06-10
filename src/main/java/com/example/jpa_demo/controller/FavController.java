package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.entity.Favorite;
import com.example.jpa_demo.service.FavoriteServiceImpl;
import com.example.jpa_demo.util.JwtToken;
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
    public BaseResponse<List<Favorite>> FavoriteList(@RequestHeader("Authorization") String tokenBearer, @PathVariable(value = "user_id")  Integer userId){
        String token = tokenBearer.substring(7,  tokenBearer.length());
        var token_id = JwtToken.decode(token).getClaim("id").asString();
        if(Integer.valueOf(token_id).intValue() != userId){
            return BaseResponse.error(10000, "无法查看非本用户的收藏列表");
        }
        return BaseResponse.success(favoriteService.listAll(userId));
    }

    @DeleteMapping("/del/{id}")
    public BaseResponse<Boolean> deleteFav(@RequestHeader("Authorization") String tokenBearer, @PathVariable Integer id) {
        String token = tokenBearer.substring(7,  tokenBearer.length());
        var token_id = JwtToken.decode(token).getClaim("id").asString();
        if(Integer.valueOf(token_id).intValue() != id){
            return BaseResponse.error(10000, "无法删除非本用户的收藏");
        }
        System.out.println(id);
        return BaseResponse.success(true);
    }

    @PostMapping("")
    public BaseResponse<Favorite> addFav(@RequestHeader("Authorization") String tokenBearer, @Valid @RequestBody FavoriteVO favoriteVO) {
        String token = tokenBearer.substring(7,  tokenBearer.length());
        var token_id = JwtToken.decode(token).getClaim("id").asString();
        if(Integer.parseInt(token_id) != favoriteVO.getUserId()){
            return BaseResponse.error(10000, "无法为非当前用户的用户添加收藏");
        }
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
