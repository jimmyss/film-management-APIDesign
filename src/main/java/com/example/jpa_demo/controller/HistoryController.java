package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.component.UserInfo;
import com.example.jpa_demo.entity.History;
import com.example.jpa_demo.service.HistoryServiceImpl;
import com.example.jpa_demo.service.MovieServiceImpl;
import com.example.jpa_demo.util.JwtToken;
import com.example.jpa_demo.vo.HistoryVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@Validated
@RestController
@RequestMapping("/api/histories")
public class HistoryController {
    @Autowired
    private HistoryServiceImpl historyService;
    @Autowired
    private MovieServiceImpl movieService;
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
    public BaseResponse<History> add(@RequestHeader("Authorization") String tokenBearer, @Valid @RequestBody HistoryVO history1) {
        String id=UserInfo.get("id");
        if(movieService.queryOverviewById(history1.getMovieId()).isEmpty()){
            return BaseResponse.error(10001, "电影不存在");
        }
        List<History> userHis=historyService.listByUserIdAndMovieId(Integer.valueOf(id), history1.getMovieId());
        if(!userHis.isEmpty()){
            //如果已经有了一条收藏记录，跟新时间戳
            return BaseResponse.success(historyService.update(userHis.get(0)));
        }
        History history = new History();
        history.setMovie_id(history1.getMovieId());
        history.setUser_id(Integer.valueOf(id));
        return BaseResponse.success(historyService.add(history));
    }

    @GetMapping("/count")
    public BaseResponse<Integer> count(@RequestParam Integer movieId) {
        return BaseResponse.success(historyService.countAll(movieId));
    }
}
