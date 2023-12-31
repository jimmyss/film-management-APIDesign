package com.example.jpa_demo.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoriteVO {
    @NotNull(message = "不能没有电影信息")
    private Integer movieId;
}
