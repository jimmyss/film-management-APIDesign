package com.example.jpa_demo;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.entity.Movie;
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
import java.util.List;

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
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(20000, baseResponse.getCode());
        List<Movie> movieList = objectMapper.readValue(objectMapper.writeValueAsString(baseResponse.getData()), List.class);
        Assertions.assertEquals("Ariel", movieList.get(0).getTitle());
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
        MvcResult result = mockMvc.perform(post("/api/movies")
                        .header("Authorization", "Bearer " + token)
                        .content("{\"title\":\"test\",\"director\":\"test\",\"year\":\"2021\",\"rating\":\"10\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(20000, baseResponse.getCode());
        List<Movie> movieList = objectMapper.readValue(objectMapper.writeValueAsString(baseResponse.getData()), List.class);
        Assertions.assertEquals("test", movieList.get(0).getTitle());
    }
    // post movie using a user role
    @Test
    void postMovieWithUserRole() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/movies")
                        .header("Authorization", "Bearer " + token)
                        .content("{\"title\":\"test\",\"director\":\"test\",\"year\":\"2021\",\"rating\":\"10\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(20000, baseResponse.getCode());
        List<Movie> movieList = objectMapper.readValue(objectMapper.writeValueAsString(baseResponse.getData()), List.class);
        Assertions.assertEquals("test", movieList.get(0).getTitle());
    }
    // delete movie by id
    @Test
    void deleteMovieById() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/movies")
                        .header("Authorization", "Bearer " + token)
                        .content("{\"title\":\"test\",\"director\":\"test\",\"year\":\"2021\",\"rating\":\"10\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        List<Movie> movieList = objectMapper.readValue(objectMapper.writeValueAsString(baseResponse.getData()), List.class);
        int id = movieList.get(0).getId();
        result = mockMvc.perform(post("/api/movies/" + id)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        response = result.getResponse().getContentAsString();
        baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(20000, baseResponse.getCode());
        movieList = objectMapper.readValue(objectMapper.writeValueAsString(baseResponse.getData()), List.class);
        Assertions.assertEquals(0, movieList.size());
    }
    // delete movie by not existing id
    @Test
    void deleteMovieByNotExistingId() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/movies/1000")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(40001, baseResponse.getCode());
    }
    // delete movie by id using a user role
    @Test
    void deleteMovieByIdWithUserRole() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/movies")
                        .header("Authorization", "Bearer " + token)
                        .content("{\"title\":\"test\",\"director\":\"test\",\"year\":\"2021\",\"rating\":\"10\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        List<Movie> movieList = objectMapper.readValue(objectMapper.writeValueAsString(baseResponse.getData()), List.class);
        int id = movieList.get(0).getId();
        result = mockMvc.perform(post("/api/movies/" + id)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        response = result.getResponse().getContentAsString();
        baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(20000, baseResponse.getCode());
        movieList = objectMapper.readValue(objectMapper.writeValueAsString(baseResponse.getData()), List.class);
        Assertions.assertEquals(0, movieList.size());
    }

}
