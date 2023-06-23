package com.example.jpa_demo;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.entity.Movie;
import com.example.jpa_demo.vo.MovieVO;
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

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        MvcResult result = mockMvc.perform(post("/api/users/login")
                        .content("{\"username\":\"admin\",\"password\":\"m2{4|'4-RRec<8H\"}")
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
    @Test
    void getMovieById() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/movies/2")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(20000, baseResponse.getCode());
    }
    // get movie by not existing id
    @Test
    void getMovieByNotExistingId() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/movies/1000")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(20000, baseResponse.getCode());
        List<Movie> movieList = objectMapper.readValue(objectMapper.writeValueAsString(baseResponse.getData()), List.class);
        Assertions.assertEquals(0, movieList.size());
    }
    // post movie
    @Test
    void postMovie() throws Exception {
        MovieVO movieVO = new MovieVO();
        movieVO.setImdbId("test");
        movieVO.setTitle("test");
        movieVO.setOverview("test");
        movieVO.setPopularity(1.0f);
        movieVO.setPosterPath("test");
        movieVO.setReleaseDate("2021-01-01");
        movieVO.setRevenue(11l);
        movieVO.setRuntime(100);

        MvcResult result = mockMvc.perform(post("/api/movies")
                        .header("Authorization", "Bearer " + token)
                        .content(movieVO.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(20000, baseResponse.getCode());
        var res = objectMapper.readValue(objectMapper.writeValueAsString(baseResponse.getData()), String.class);
        Assertions.assertEquals("添加成功", res);
    }

    // post movie without title
    @Test
    void postMovieWithoutTitle() throws Exception {
        MovieVO movieVO = new MovieVO();
        movieVO.setImdbId("test");
        movieVO.setTitle("");
        movieVO.setOverview("test");
        movieVO.setPopularity(1.0f);
        movieVO.setPosterPath("test");
        movieVO.setReleaseDate("2021-01-01");
        movieVO.setRevenue(11l);
        movieVO.setRuntime(100);

        MvcResult result = mockMvc.perform(post("/api/movies")
                        .header("Authorization", "Bearer " + token)
                        .content(movieVO.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        var response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(-1, baseResponse.getCode());
        var msg = objectMapper.readValue(objectMapper.writeValueAsString(baseResponse.getMsg()), String.class);
        Assertions.assertEquals("缺少标题", msg);
    }

    // post movie using a user role
    @Test
    void postMovieWithUserRole() throws Exception {
        // 模拟登录请求
        MvcResult result = mockMvc.perform(post("/api/users/login")
                        .content("{\"username\":\"test\",\"password\":\"test\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // 从响应中获取 token
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        HashMap<String, String> data = (HashMap<String, String>) baseResponse.getData();
        var token = data.get("token");

        // post movie
        MovieVO movieVO = new MovieVO();
        movieVO.setImdbId("test");
        movieVO.setTitle("test");
        movieVO.setOverview("test");
        movieVO.setPopularity(1.0f);
        movieVO.setPosterPath("test");
        movieVO.setReleaseDate("2021-01-01");
        movieVO.setRevenue(11l);
        movieVO.setRuntime(100);

        MvcResult result2 = mockMvc.perform(post("/api/movies")
                        .header("Authorization", "Bearer " + token)
                        .content(movieVO.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        var response2 = result2.getResponse().getContentAsString(StandardCharsets.UTF_8);
        BaseResponse baseResponse2 = objectMapper.readValue(response2, BaseResponse.class);
        Assertions.assertEquals(-1, baseResponse2.getCode());
        var msg = objectMapper.readValue(objectMapper.writeValueAsString(baseResponse2.getMsg()), String.class);
        Assertions.assertEquals("权限不足", msg);
    }

    // delete movie by id using a user role
    @Test
    void deleteMovieByIdWithUserRole() throws Exception {
        // 模拟登录请求
        MvcResult result = mockMvc.perform(post("/api/users/login")
                        .content("{\"username\":\"test\",\"password\":\"test\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // 从响应中获取 token
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        HashMap<String, String> data = (HashMap<String, String>) baseResponse.getData();
        var token = data.get("token");

        result = mockMvc.perform(delete("/api/movies/" + 1)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(-1, baseResponse.getCode());
        var msg = objectMapper.readValue(objectMapper.writeValueAsString(baseResponse.getMsg()), String.class);
        Assertions.assertEquals("权限不足", msg);
    }

}
