package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.component.UserInfo;
import com.example.jpa_demo.entity.History;
import com.example.jpa_demo.service.HistoryServiceImpl;
import com.example.jpa_demo.service.MovieServiceImpl;
import com.example.jpa_demo.vo.HistoryVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public BaseResponse<Page<History>> getHistoryList(
            @RequestParam(required = false, defaultValue = "0")
            @Min(value = 0, message = "Page number should be a positive number or 0") Integer page,
            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 0, message = "Page size should be a positive number") Integer size
    ) {
        String id = UserInfo.get("id");
        return BaseResponse.success(historyService.listAll(Integer.valueOf(id), page, size));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Boolean> deleteHistory(@PathVariable Integer id) {
        return BaseResponse.success(historyService.delete(id));
    }

    @PostMapping("")
    public BaseResponse<History> add(@Valid @RequestBody HistoryVO history1) {
        String id = UserInfo.get("id");
        if (movieService.queryOverviewById(history1.getMovieId()).isEmpty()) {
            return BaseResponse.error(10001, "电影不存在");
        }
        List<History> userHis = historyService.listByUserIdAndMovieId(Integer.valueOf(id), history1.getMovieId());
        if (!userHis.isEmpty()) {
            //如果已经有了一条收藏记录，跟新时间戳
            return BaseResponse.success(historyService.update(userHis.get(0)));
        }
        History history = new History();
        history.setMovieId(history1.getMovieId());
        history.setUserId(Integer.valueOf(id));
        return BaseResponse.success(historyService.add(history));
    }

    @GetMapping("/count")
    public BaseResponse<Integer> count(@RequestParam Integer movieId) {
        return BaseResponse.success(historyService.countAll(movieId));
    }
}
