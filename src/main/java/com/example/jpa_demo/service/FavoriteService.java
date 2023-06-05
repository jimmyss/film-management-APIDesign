package com.example.jpa_demo.service;

import com.example.jpa_demo.entity.Favorite;

import java.util.List;

public interface FavoriteService {
    Favorite add(Favorite favorite);
    List<Favorite> listAll(Integer userId);
    int countAll(Integer userId);
    boolean delete(Integer id);

}
