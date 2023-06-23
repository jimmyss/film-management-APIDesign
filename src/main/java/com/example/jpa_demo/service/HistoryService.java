package com.example.jpa_demo.service;

import com.example.jpa_demo.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author jerry
 */
public interface HistoryService {
    History add(History history);
    Page<History> listAll(Integer userId, Integer page, Integer size);
    int countAll(Integer movieId);
    boolean delete(Integer id);
    List<History> listByUserIdAndMovieId(Integer userId, Integer movieId);
    History update(History history);

}
