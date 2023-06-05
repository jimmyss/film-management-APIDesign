package com.example.jpa_demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="add_favorite")
public class Favorite {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//strategy=GenerationType.IDENTITY 自增长
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "movie_id")
    private Integer movieId;
}
