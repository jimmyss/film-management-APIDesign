package com.example.jpa_demo.repository;

import com.example.jpa_demo.entity.Favorite;
import com.example.jpa_demo.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query(value = "select * from movie where id = ?", nativeQuery = true)
    List<Movie> queryMovieOverviewById(Integer id);
}
