package com.example.jpa_demo.service;

import com.example.jpa_demo.entity.History;
import com.example.jpa_demo.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class HistoryServiceImpl implements HistoryService{
    @Autowired
    private HistoryRepository historyRepository;
    @Override
    public History add(History history) {
        History history1 = new History();
        history1.setUserId(history.getUserId());
        history1.setMovieId(history.getMovieId());
        history1.setTime(LocalDateTime.now());
        return historyRepository.save(history1);
    }

    @Override
    public Page<History> listAll(Integer userId, Integer page, Integer size) {
        Pageable pageable= PageRequest.of(page, size);
        return historyRepository.findByUserId(userId, pageable);
    }

    @Override
    public int countAll(Integer movieId) {
        return historyRepository.countHistoriesByMovieId(movieId);
    }

    @Override
    public boolean delete(Integer id) {
        try {
            this.historyRepository.deleteById(id);
            return true;
        }catch (Exception e){
            System.out.println("浏览历史不存在");
            return false;
        }
    }

    @Override
    public List<History> listByUserIdAndMovieId(Integer userId, Integer movieId){
        return historyRepository.findHistoryByUserIdAndMovieId(userId, movieId);
    }

    @Override
    public History update(History history){
        try{
            history.setTime(LocalDateTime.now());
            return historyRepository.save(history);
        }catch (Exception e){
            return null;
        }
    }
}
