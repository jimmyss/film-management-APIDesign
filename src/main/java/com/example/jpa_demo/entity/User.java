package com.example.jpa_demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
@Entity
@Table(name="user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//strategy=GenerationType.IDENTITY 自增长
    @Column(name = "user_id")
    private Integer Id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    public User(){
    }
    public User(String name, String password){
        userName =  name;
        this.password =password;
    }

}
