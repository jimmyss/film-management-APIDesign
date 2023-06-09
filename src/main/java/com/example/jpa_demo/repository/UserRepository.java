package com.example.jpa_demo.repository;

import com.example.jpa_demo.entity.User;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
=======
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
>>>>>>> zrt
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
<<<<<<< HEAD
@EnableJpaRepositories()
=======


>>>>>>> zrc
public interface UserRepository extends JpaRepository<User, Integer>{
}
=======
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findUserByUsernameAndPassword(String username, String password);
}
>>>>>>> zrt
