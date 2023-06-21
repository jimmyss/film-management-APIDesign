package com.example.jpa_demo.service;

import com.example.jpa_demo.entity.Movie;
import com.example.jpa_demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MovieServiceImpl implements MovieService{
    @Autowired
    private MovieRepository movieRepository;
    @Override
    public List<Movie> queryOverviewById(Integer id) {
        return movieRepository.queryMovieOverviewById(id);
    }
}
