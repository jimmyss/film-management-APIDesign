package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.component.UserInfo;
import com.example.jpa_demo.entity.History;
import com.example.jpa_demo.service.HistoryServiceImpl;
import com.example.jpa_demo.util.JwtToken;
import com.example.jpa_demo.vo.HistoryVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
@RequestMapping("/api/histories")
public class HistoryController {
    @Autowired
    private HistoryServiceImpl historyService;
    @GetMapping("")
    public BaseResponse<List<History>> getHistoryList(@RequestHeader("Authorization") String tokenBearer){
        String id= UserInfo.get("id");
        return BaseResponse.success(historyService.listAll(Integer.valueOf(id)));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Boolean> deleteHistory(@RequestHeader("Authorization") String tokenBearer, @PathVariable Integer id) {
        String token=tokenBearer.substring(7, tokenBearer.length());
        var token_id= JwtToken.decode(token).getClaim("id").asString();
        return BaseResponse.success(historyService.delete(id));
    }

    @PostMapping("")
    public BaseResponse<History> add(@Valid @RequestBody HistoryVO history1) {
        History history = new History();
        history.setMovie_id(history1.getMovieId());
        history.setUser_id(history1.getUserId());
        return BaseResponse.success(historyService.add(history));
    }

    @GetMapping("/movies/{movie_id}")
    public BaseResponse<Integer> count(@PathVariable(value = "movie_id") Integer movieId) {
        return BaseResponse.success(historyService.countAll(movieId));
    }
}
