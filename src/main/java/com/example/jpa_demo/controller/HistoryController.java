package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.component.UserInfo;
import com.example.jpa_demo.entity.History;
import com.example.jpa_demo.service.HistoryServiceImpl;
import com.example.jpa_demo.service.MovieServiceImpl;
import com.example.jpa_demo.vo.HistoryVO;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/histories")
public class HistoryController {
    @Autowired
    private HistoryServiceImpl historyService;
    @Autowired
    private MovieServiceImpl movieService;

    @GetMapping("")
    public BaseResponse<Page<History>> getHistoryList(
            @RequestParam(required = false, defaultValue = "0")
            @Min(value = 0, message = "页码必须大于等于 0") Integer page,
            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 0, message = "页面大小必须大于0") Integer size
    ) {
        String id = UserInfo.get("id");
        return BaseResponse.success(historyService.listAll(Integer.valueOf(id), page, size));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Boolean> deleteHistory(@PathVariable Integer id) {
        return BaseResponse.success(historyService.delete(id));
    }

    @PostMapping("")
    public BaseResponse<History> add(@RequestBody @Validated HistoryVO historyVO) {
        String id = UserInfo.get("id");
        if (movieService.queryOverviewById(historyVO.getMovieId()).isEmpty()) {
            return BaseResponse.error(10001, "电影不存在");
        }
        List<History> userHis = historyService.listByUserIdAndMovieId(Integer.valueOf(id), historyVO.getMovieId());
        if (!userHis.isEmpty()) {
            //如果已经有了一条收藏记录，跟新时间戳
            userHis.get(0).setTime(LocalDateTime.now());
            return BaseResponse.success(historyService.update(userHis.get(0)));
        }
        History history = new History();
        history.setMovieId(historyVO.getMovieId());
        history.setUserId(Integer.valueOf(id));
        history.setTime(LocalDateTime.now());
        return BaseResponse.success(historyService.add(history));
    }

    @GetMapping("/count")
    public BaseResponse<Integer> count(@RequestParam Integer movieId) {
        return BaseResponse.success(historyService.countAll(movieId));
    }
}
