package com.example.jpa_demo.entity;

import com.example.jpa_demo.component.LocalDateTimeConverter;
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
    private Integer userId;
    @Column(name = "Movie_id")
    private Integer movieId;
    @Column(name = "time")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime time;

}
