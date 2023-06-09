package com.example.jpa_demo.entity;

import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "has_history")
public class History {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//strategy=GenerationType.IDENTITY 自增长
    @Column(name = "id")
    private Integer id;
    @Column(name = "User_id")
    private Integer user_id;
    @Column(name = "Movie_id")
    private Integer movie_id;
    @Column(name = "time")
    private LocalDateTime time;

}
