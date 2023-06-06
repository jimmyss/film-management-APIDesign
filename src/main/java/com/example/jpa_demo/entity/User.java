package com.example.jpa_demo.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//strategy=GenerationType.IDENTITY 自增长
    private Integer user_id;
    @Column(name="user_name")
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
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
