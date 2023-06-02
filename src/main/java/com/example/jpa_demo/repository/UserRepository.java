package com.example.jpa_demo.repository;

import com.example.jpa_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@EnableJpaRepositories()
public interface UserRepository extends JpaRepository<User, Integer>{
}