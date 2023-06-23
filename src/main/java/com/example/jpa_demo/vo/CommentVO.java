package com.example.jpa_demo.vo;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @projectName: apidesign
 * @package: com.example.jpa_demo.vo
 * @className: CommentVO
 * @author: Dushimao
 * @description: TODO
 * @date: 2023/6/22 14:40
 * @version: 1.0
 */
@Data
public class CommentVO {
    @NotNull(message = "必须填电影id")
    private Integer movieId;

    @NotNull(message = "必须填评分")
    @Min(value = 0, message = "评分必须大于0")
    @Max(value = 10, message = "评分必须小于10")
    private Float rate;

    @Nullable
    private String comment;
}
