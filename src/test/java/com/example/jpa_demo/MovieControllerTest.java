package com.example.jpa_demo;

import com.example.jpa_demo.component.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
@AutoConfigureMockMvc
public class MovieControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private String token;

    @BeforeEach
    public void setUp() throws Exception {
        // 模拟登录请求
        MvcResult result = mockMvc.perform(post("/api/user/login")
                        .content("{\"username\":\"admin\",\"password\":\"admin\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // 从响应中获取 token
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        HashMap<String, String> data = (HashMap<String, String>) baseResponse.getData();
        this.token = data.get("token");
        System.out.println("token: " + token);
    }

    // get movies
    @Test
    void getMovies() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(20000, baseResponse.getCode());
    }
    // get movie by id

    // post movie

    // delete movie by id


}
