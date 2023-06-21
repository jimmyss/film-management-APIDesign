package com.example.jpa_demo;

import com.example.jpa_demo.entity.Favorite;
import com.example.jpa_demo.repository.FavoriteRepository;
import com.example.jpa_demo.repository.MovieRepository;
import com.example.jpa_demo.service.FavoriteServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaDemoApplication.class)
public class FavRepositoryTest {
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private FavoriteServiceImpl favoriteService;
    @Autowired
    private MovieRepository movieRepository;
    @Test
    public void testInsertFav(){
        Favorite favorite = new Favorite();
<<<<<<< HEAD
        favorite.setUserId(Integer.valueOf("1"));
        favorite.setMovieId(Integer.valueOf("4"));
=======
//        favorite.setUserId("1");
//        favorite.setMovieId("4");
>>>>>>> e85acd16415627b26534e30c62f8f4a4e4821393
        System.out.println(this.favoriteRepository.save(favorite));
    }
    @Test
    public void testSearchFav(){
//        System.out.println(this.favoriteRepository.queryFavByUserIdAndMovieId(1,3));
        System.out.println(this.favoriteRepository.queryFavById(4));
    }
    @Test
    public void testCountFav(){
    }
    @Test
    public void testDeleteFav(){
       System.out.println(favoriteService.delete(3));
    }
    @Test
    public void testMovieFav(){
        System.out.println(movieRepository.queryMovieOverviewById(2));
    }

}
