package com.example.jpa_demo.service;

import com.example.jpa_demo.entity.History;
import com.example.jpa_demo.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        history1.setUser_id(history.getUser_id());
        history1.setMovie_id(history.getMovie_id());
        history1.setTime(LocalDateTime.now());
        return historyRepository.save(history);
    }

    @Override
    public List<History> listAll(Integer userId) {
        return historyRepository.queryHistoryByUser_id(userId);
    }

    @Override
    public int countAll(Integer movieId) {
        List<History> historyList = historyRepository.queryHistoryByMovie_id(movieId);
        return historyList.size();
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
    }
