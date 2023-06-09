package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.entity.History;
import com.example.jpa_demo.service.HistoryServiceImpl;
import com.example.jpa_demo.vo.HistoryVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
    @Validated
    @RestController
    @RequestMapping("/api/history")
public class HistoryController {
    @Autowired
    private HistoryServiceImpl historyService;
    @GetMapping("/list/{user_id}")
    public BaseResponse<List<History>> HistoryList(@PathVariable(value = "user_id")  Integer userId){
        System.out.println(userId);
        return BaseResponse.success(historyService.listAll(userId));
    }

    @DeleteMapping("/del/{id}")
    public BaseResponse<Boolean> deleteHistory(@PathVariable Integer id) {
        System.out.println(id);
        return BaseResponse.success(historyService.delete(id));
    }

    @PostMapping("")
    public BaseResponse<History> add(@Valid @RequestBody HistoryVO history1) {
        History history = new History();
        history.setMovie_id(history1.getMovieId());
        history.setUser_id(history1.getUserId());
        System.out.println(history);
        return BaseResponse.success(historyService.add(history));
    }

    @GetMapping("/count/{movie_id}")
    public BaseResponse<Integer> count(@PathVariable(value = "movie_id") Integer movieId) {
        System.out.println(movieId);
        return BaseResponse.success(historyService.countAll(movieId));
    }
}
