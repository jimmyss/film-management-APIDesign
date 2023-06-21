package com.example.jpa_demo.repository;

import com.example.jpa_demo.entity.Favorite;
import com.example.jpa_demo.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    @Query(value = "select * from add_favorite where user_id = ?", nativeQuery = true)
    List<Favorite> queryFavByUser_id(Integer userId);

    @Query(value = "select * from add_favorite where movie_id = ?", nativeQuery = true)
    List<Favorite> queryFavByMovie_id(Integer movieId);


//    @Modifying
//    @Transactional
//    @Query(value = "delete  from add_favorite where id = ?", nativeQuery = true)
//    void deleteFavById(Integer userId);
//    @Modifying
//    void deleteFavoriteById(Integer id);
}