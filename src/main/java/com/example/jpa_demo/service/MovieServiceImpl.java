package com.example.jpa_demo.service;

import com.example.jpa_demo.entity.Movie;
import com.example.jpa_demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public void deleteById(Integer id) {
        movieRepository.deleteById(id);
    }

    @Override
    public void addMovie(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public Page<Movie> queryByTitle(String title, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return movieRepository.findByTitleContaining(title, pageable);
    }

    @Override
    public List<Movie> findById(Integer movieId){return movieRepository.findMovieById(movieId);}

    @Override
    public Page<Movie> queryAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return movieRepository.findAll(pageable);
    }
}
