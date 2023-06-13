package com.example.jpa_demo.controller;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.entity.User;
import com.example.jpa_demo.service.UserService;
import com.example.jpa_demo.util.JwtToken;
import com.example.jpa_demo.vo.UserBaseVO;
import com.example.jpa_demo.vo.UserRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    BaseResponse login(@RequestBody @Validated UserBaseVO userBaseVO) {
        System.out.println(userBaseVO);

        return userService.login(userBaseVO.getUsername(), userBaseVO.getPassword());
    }


    @PostMapping("/register")
    BaseResponse register(@RequestBody UserRegisterVO userRegisterVO) {
        System.out.println(userRegisterVO);
        String username = userRegisterVO.getUsername();
        String password = userRegisterVO.getPassword();

        return userService.register(username, password);
    }



}

