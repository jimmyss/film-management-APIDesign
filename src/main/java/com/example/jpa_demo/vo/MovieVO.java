package com.example.jpa_demo.vo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovieVO {
    @NotEmpty(message = "缺少imdbId")
    private String imdbId;

    @NotEmpty(message = "缺少发布语言")
    private String originalLanguage;

    @NotEmpty(message = "缺少介绍")
    private String overview;

    @NotNull(message = "缺少人气")
    @Min(value = 0, message = "人气不能为负数")
    private Float popularity;

    @NotEmpty(message = "缺少海报")
    private String posterPath;

    @NotEmpty(message = "缺少发布日期")
    private String releaseDate;

    @NotNull(message = "缺少收入")
    private Long revenue;

    @NotNull(message = "缺少时长")
    @Min(value = 0, message = "时长不能为负数")
    private Integer runtime;

    @NotEmpty(message = "缺少标题")
    private String title;
}
