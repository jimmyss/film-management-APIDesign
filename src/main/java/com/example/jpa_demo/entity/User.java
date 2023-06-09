package com.example.jpa_demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name="user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//strategy=GenerationType.IDENTITY 自增长
    private Integer user_id;
    @Column(name="user_name")
<<<<<<< HEAD
    private String user_name;
    @Column(name="password")
    private String password;

<<<<<<< HEAD
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
=======
    private String username;
    @Column(name="password")
    private String password;

    public Integer getId() {
        return user_id;
    }

    public User(Integer user_id, String username, String password) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUser_name(String user_name) {
        this.username = user_name;
>>>>>>> zrt
    }

    public void setPassword(String password) {
        this.password = password;
    }

=======
>>>>>>> zrc
    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
<<<<<<< HEAD
                ", user_name='" + user_name + '\'' +
=======
                ", user_name='" + username + '\'' +
>>>>>>> zrt
                ", password='" + password + '\'' +
                '}';
    }
}
