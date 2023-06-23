package com.example.jpa_demo.repository;

import com.example.jpa_demo.entity.History;
import com.example.jpa_demo.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Integer> {
//    @Query(value = "select * from has_history where user_id = ?", nativeQuery = true)

    Page<History> findByUserId(Integer userId, Pageable pageable);

    Integer countHistoriesByMovieId(Integer movieId);

    List<History> findHistoryByUserIdAndMovieId(Integer userId, Integer movieId);
}
