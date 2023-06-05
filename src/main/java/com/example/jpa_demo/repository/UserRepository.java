package com.example.jpa_demo.repository;

import com.example.jpa_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer>{
}