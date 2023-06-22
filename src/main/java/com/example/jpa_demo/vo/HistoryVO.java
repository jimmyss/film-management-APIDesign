package com.example.jpa_demo.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jerry
 */
@Data
public class HistoryVO {
    @NotNull(message = "不能没有电影信息")
    private int movieId;
}
