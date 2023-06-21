package com.example.jpa_demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="movie")
public class Movie {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//strategy=GenerationType.IDENTITY 自增长
    @Column(name = "id")
    private Integer id;
    @Column(name = "imdb_id")
    private String imdbId;
    @Column(name = "original_language")
    private String originalLanguage;
    @Column(name = "overview")
    private String overview;
    @Column(name = "popularity")
    private float popularity;
    @Column(name = "poster_path")
    private String posterPath;
    @Column(name = "release_date")
    private String releaseDate;
    @Column(name = "revenue")
    private int revenue;
    @Column(name = "runtime")
    private int runtime;
    @Column(name = "title")
    private String title;
}
