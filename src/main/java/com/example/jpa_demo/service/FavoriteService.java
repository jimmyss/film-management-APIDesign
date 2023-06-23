package com.example.jpa_demo.service;

import com.example.jpa_demo.entity.Favorite;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FavoriteService {
    Favorite add(Favorite favorite);
    Page<Favorite> listAll(Integer userId, Integer page, Integer size);
    int countAll(Integer movieId);
    boolean delete(Integer id);
    List<Favorite> listById(Integer id);
    List<Favorite> listByUserIdAndMovieId(Integer userId, Integer movieId);

}
