package com.example.jpa_demo;

import com.example.jpa_demo.entity.History;
import com.example.jpa_demo.repository.HistoryRepository;
import com.example.jpa_demo.repository.MovieRepository;
import com.example.jpa_demo.service.HistoryServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaDemoApplication.class)
public class HistoryRepositoryTest {
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private HistoryServiceImpl historyService;
    @Autowired
    private MovieRepository movieRepository;
    @Test
    public void testInsert(){
        History history = new History();
        history.setUser_id(Integer.valueOf(1));
        history.setMovie_id(Integer.valueOf(11));
        history.setTime(LocalDateTime.now());
        System.out.println(this.historyRepository.save(history));
    }
    @Test
    public void testSearch(){
        System.out.println(this.historyRepository.queryHistoryByUser_id(1));
    }
    @Test
    public void testCount(){
        System.out.println(historyService.countAll(11));
    }
    @Test
    public void testDelete(){
       System.out.println(historyService.delete(4));
    }
    @Test
    public void testMovie(){
        System.out.println(movieRepository.queryMovieOverviewById(2));
    }

}
