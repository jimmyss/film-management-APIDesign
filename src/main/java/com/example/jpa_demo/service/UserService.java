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
        //map.put("id", String.valueOf(user.getUsername()));
        map.put("username", user.getUsername());

        String token = JwtToken.create(map);

        map.put("token", token);
        return BaseResponse.success(map);
    }

    public BaseResponse<Map<String, String>> register(String username, String password) {
        //md5加密储存
        String plainPassword = password;
        String ciphertext = DigestUtils.md5DigestAsHex(plainPassword.getBytes());
        User user1 = userRepository.findUserByUsernameAndPassword(username, ciphertext);

        //没查到返回登录失败
        if(user1 != null) {
            return BaseResponse.error("已有相同账号");
        }


        User user = new User(null, username, ciphertext);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return BaseResponse.error("该账号已被注册");
        }

        var res = new HashMap<String, String>();
        // token生成
        var map = new LinkedHashMap<String, String>();
        map.put("status", "ok");
        return BaseResponse.success(res);
    }
}
