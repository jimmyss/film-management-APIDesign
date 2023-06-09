package com.example.jpa_demo.service;

import com.example.jpa_demo.entity.History;

import java.util.List;

/**
 * @author jerry
 */
public interface HistoryService {
    History add(History history);
    List<History> listAll(Integer userId);
    int countAll(Integer movieId);
    boolean delete(Integer id);

}
