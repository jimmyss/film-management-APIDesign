package com.example.jpa_demo.service;

import com.example.jpa_demo.entity.Favorite;
import com.example.jpa_demo.entity.Movie;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MovieService {
    List<Movie> queryOverviewById(Integer id);

    void deleteById(Integer id);

    void addMovie(Movie movie);

    Page<Movie> queryByTitle(String title, Integer page, Integer size);

    List<Movie> findById(Integer movieId);

    Page<Movie> queryAll(Integer page, Integer size);
}
