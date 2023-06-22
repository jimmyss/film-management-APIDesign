package com.example.jpa_demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: apidesign
 * @package: com.example.jpa_demo.entity
 * @className: Comment
 * @author: Dushimao
 * @description: TODO
 * @date: 2023/6/22 14:06
 * @version: 1.0
 */
@Data
@Builder
@Entity(name = "give_comment")
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "movie_id")
    private Integer movieId;

    private Float rate;

    private String comment;
}
