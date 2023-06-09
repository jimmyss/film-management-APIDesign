package com.example.jpa_demo.repository;

import com.example.jpa_demo.entity.History;
import com.example.jpa_demo.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Integer> {
    @Query(value = "select * from has_history where user_id = ?", nativeQuery = true)
    List<History> queryHistoryByUser_id(Integer userId);

    @Query(value = "select * from has_history where movie_id = ?", nativeQuery = true)
    List<History> queryHistoryByMovie_id(Integer movieId);

//    @Query
//    List<History>   queryHistoryByTime(LocalDateTime time)
}
