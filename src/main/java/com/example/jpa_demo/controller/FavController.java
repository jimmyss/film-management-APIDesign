package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.component.UserInfo;
import com.example.jpa_demo.entity.Favorite;
import com.example.jpa_demo.service.FavoriteServiceImpl;
import com.example.jpa_demo.service.MovieServiceImpl;
import com.example.jpa_demo.util.JwtToken;
import com.example.jpa_demo.vo.FavoriteVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
@RequestMapping("/api/favorites")
public class FavController {
    @Autowired
    private FavoriteServiceImpl favoriteService;
    @Autowired
    private MovieServiceImpl movieService;

    @GetMapping ("")
    public BaseResponse<Page<Favorite>> FavoriteList(@RequestParam(required = false, defaultValue = "0")
                                                         @Min(value = 0, message = "页码必须大于等于 0") Integer page,
                                                     @RequestParam(required = false, defaultValue = "10")
                                                         @Min(value = 0, message = "页面大小必须大于0") Integer size){
        String id = UserInfo.get("id");
        return BaseResponse.success(favoriteService.listAll(Integer.valueOf(id), page, size));
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
        favoriteService.delete(id);
        return BaseResponse.success(true);
    }

    @PostMapping("")
    public BaseResponse<Favorite> addFav(@RequestHeader("Authorization") String tokenBearer, @Valid @RequestBody FavoriteVO favoriteVO) {
        String userId = UserInfo.get("id");

        var movieList = movieService.queryOverviewById(favoriteVO.getMovieId());
        if(movieList.isEmpty()){
            return BaseResponse.error(10003, "电影不存在");
        }
        if(favoriteService.listByUserIdAndMovieId(Integer.valueOf(userId), favoriteVO.getMovieId()).size() != 0){
            return BaseResponse.error(10002, "收藏已存在，无需添加");
        }
        Favorite favorite = new Favorite();
        favorite.setMovie(movieList.get(0));
        favorite.setUserId(Integer.valueOf(userId));
        return BaseResponse.success(favoriteService.add(favorite));
    }

    @GetMapping("/count")
    public BaseResponse<Integer> countFav(@RequestParam Integer movieId) {
        if(movieService .queryOverviewById(movieId).isEmpty()){
            return BaseResponse.error(10003, "电影不存在");
        }
        return BaseResponse.success(favoriteService.countAll(movieId));
    }
}
