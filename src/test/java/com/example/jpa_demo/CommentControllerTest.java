package com.example.jpa_demo;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.controller.CommentController;
import com.example.jpa_demo.service.CommentServiceImpl;
import com.example.jpa_demo.service.MovieServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.print.attribute.standard.Media;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * @projectName: apidesign
 * @package: com.example.jpa_demo
 * @className: CommentControllerTest
 * @author: Dushimao
 * @description: TODO
 * @date: 2023/6/23 11:42
 * @version: 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private String token;
    @Mock
    private CommentServiceImpl commentService;
    @Mock
    private MovieServiceImpl movieService;
    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    public void setUp() throws Exception{
        //模拟登录请求
        MvcResult result=mockMvc.perform(post("/api/users/login")
                            .content("{\"username\":\"dushimao\",\"password\":\"password\"}")
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

    @Test
    public void testGetMovieCommentHaveComments() throws Exception{
        //create Instance
        //TODO:测试当电影有评论，一切正常的情况
        //perform
        MvcResult result=mockMvc.perform(get("/api/comments")
                        .header("Authorization", "Bearer "+token)
                        .param("movieId", "5")
                        .param("page", "0")
                        .param("size","10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Integer code = baseResponse.getCode();
        Assertions.assertEquals((long)20000, (long)code);
    }

    @Test
    public void testGetMovieCommentNoComment() throws Exception{
        //create Instance
        //TODO:测试当电影没有评论的时候
        //perform
        MvcResult result=mockMvc.perform(get("/api/comments")
                        .header("Authorization", "Bearer "+token)
                        .param("movieId", "1")
                        .param("page", "0")
                        .param("size","10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Integer code = baseResponse.getCode();
        Assertions.assertEquals((long)10004, (long)code);
    }

    @Test
    public void testGetMovieCommentNoMovie() throws Exception{
        //create Instance
        //TODO:测试当电影不存在的时候
        //perform
        MvcResult result=mockMvc.perform(get("/api/comments")
                        .header("Authorization", "Bearer "+token)
                        .param("movieId", "4")
                        .param("page", "0")
                        .param("size","10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Integer code = baseResponse.getCode();
        Assertions.assertEquals((long)10001, (long)code);
    }

    @Test
    public void testGetMovieCommentNotCorrectPage() throws Exception{
        //create Instance
        //TODO:测试当page参数错误的情况
        //perform
        MvcResult result=mockMvc.perform(get("/api/comments")
                        .header("Authorization", "Bearer "+token)
                        .param("movieId", "4")
                        .param("page", "-1")
                        .param("size","10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Integer code = baseResponse.getCode();
        Assertions.assertEquals((long)-1, (long)code);
    }

    @Test
    public void testDeleteByMovieId(){

    }

    @Test
    public void testCommentAndRateOnMovie(){

    }

}
