package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.component.UserInfo;
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
    @GetMapping ("")
    public BaseResponse<List<Favorite>> FavoriteList(@RequestHeader("Authorization") String tokenBearer){
        String id = UserInfo.get("id");
        return BaseResponse.success(favoriteService.listAll(Integer.valueOf(id)));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Boolean> deleteFav(@RequestHeader("Authorization") String tokenBearer, @PathVariable Integer id) {
        String token = tokenBearer.substring(7,  tokenBearer.length());
        var token_id = JwtToken.decode(token).getClaim("id").asString();
        List<Favorite> fav = favoriteService.listById(id);
        if(fav.size() == 0){
            return BaseResponse.error(10001, "该收藏不存在，无法删除");
        }
        if(Integer.valueOf(token_id).intValue() != fav.get(0).getUserId()){
            return BaseResponse.error(10000, "无法删除非本用户的收藏");
        }
        Favorite favorite=new Favorite();
        return BaseResponse.success(true);
    }

    @PostMapping("")
    public BaseResponse<Favorite> addFav(@RequestHeader("Authorization") String tokenBearer, @Valid @RequestBody FavoriteVO favoriteVO) {
        String id = UserInfo.get("id");
        String token = tokenBearer.substring(7,  tokenBearer.length());
        if(favoriteService.listByUserIdAndMovieId(Integer.valueOf(id), favoriteVO.getMovieId()).size() != 0){
            return BaseResponse.error(10002, "收藏已存在，无需添加");
        }
        Favorite favorite = new Favorite();
        favorite.setMovieId(favoriteVO.getMovieId());
        favorite.setUserId(Integer.valueOf(id));
        return BaseResponse.success(favoriteService.add(favorite));
    }

    @GetMapping("/movies/{movieId}")
    public BaseResponse<Integer> countFav(@PathVariable(value = "movieId") Integer movieId) {
        return BaseResponse.success(favoriteService.countAll(movieId));
    }
}
