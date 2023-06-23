package com.example.jpa_demo.service;

import com.example.jpa_demo.entity.Favorite;
import com.example.jpa_demo.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService{
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Override
    public Favorite add(Favorite favorite){
        Favorite favorite1 = new Favorite();
        favorite1.setUserId(favorite.getUserId());
        favorite1.setMovieId(favorite.getMovieId());
        return favoriteRepository.save(favorite1);
    }
    @Override
    public Page<Favorite> listAll(Integer userId, Integer page, Integer size){
        Pageable pageable= PageRequest.of(page, size);
        return favoriteRepository.findFavoritesByUserId(userId, pageable);
    }
    public int countAll(Integer movieId){
        return favoriteRepository.queryFavByMovie_id(movieId).size();
    }
    @Override
    public boolean delete(Integer id){
        try {
            this.favoriteRepository.deleteById(id);
            return true;
        }catch (Exception e){
            System.out.println("收藏不存在");
            return false;
        }
    }

    @Override
    public List<Favorite> listById(Integer userId) {
        return favoriteRepository.queryFavById(userId);
    }

    @Override
    public List<Favorite> listByUserIdAndMovieId(Integer userId, Integer movieId) {
        return favoriteRepository.queryFavByUserIdAndMovieId(userId, movieId);
    }
}
