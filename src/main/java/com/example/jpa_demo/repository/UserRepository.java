package com.example.jpa_demo.repository;

import com.example.jpa_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories()

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findUserByUsernameAndPassword(String username, String password);
}
