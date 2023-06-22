package com.example.jpa_demo.service;

import com.example.jpa_demo.entity.Favorite;
import com.example.jpa_demo.entity.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> queryOverviewById(Integer id);

    void deleteById(Integer id);

    void addMovie(Movie movie);

    List<Movie> queryByTitle(String title);

    List<Movie> findById(Integer movieId);
}
