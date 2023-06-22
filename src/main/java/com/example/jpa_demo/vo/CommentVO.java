package com.example.jpa_demo.vo;

import jakarta.annotation.Nullable;
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
    @Nullable
    private Float rate;
    @Nullable
    private String comment;
}
