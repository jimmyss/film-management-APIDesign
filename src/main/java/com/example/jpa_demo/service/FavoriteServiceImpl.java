package com.example.jpa_demo.service;

import com.example.jpa_demo.entity.Favorite;
import com.example.jpa_demo.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Favorite> listAll(Integer userId){
        return favoriteRepository.queryFavByUser_id(userId);
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


}
