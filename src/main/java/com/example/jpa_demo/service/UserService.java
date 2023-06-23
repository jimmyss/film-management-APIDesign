package com.example.jpa_demo.service;

import com.example.jpa_demo.component.BaseResponse;

import com.example.jpa_demo.repository.UserRepository;
import com.example.jpa_demo.entity.User;
import com.example.jpa_demo.util.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public BaseResponse login(String username, String password) {
        //md5加密处理
        String plainPassword = password;
        String ciphertext = DigestUtils.md5DigestAsHex(plainPassword.getBytes());

        User user = userRepository.findUserByUsernameAndPassword(username, ciphertext);

        //没查到返回登录失败
        if(user == null) {
            return BaseResponse.error("登录失败");
        }

        // token生成
        var map = new LinkedHashMap<String, String>();
        map.put("id", String.valueOf(user.getUser_id()));
        map.put("username", user.getUsername());
        if(user.getRole().equals(1))
            map.put("role", "admin");
        else
            map.put("role", "user");
        String token = JwtToken.create(map);

        map.put("token", token);
        return BaseResponse.success(map);
    }

    public BaseResponse<String> register(String username, String password) {
        User user1 = userRepository.findUserByUsername(username);

        //没查到返回登录失败
        if(user1 != null) {
            return BaseResponse.error("该账号已被注册");
        }

        //md5加密储存
        String plainPassword = password;
        String ciphertext = DigestUtils.md5DigestAsHex(plainPassword.getBytes());
        User user = new User(username, ciphertext);
        userRepository.save(user);

        return BaseResponse.success("注册成功");
    }
}
