package com.example.jpa_demo;

import com.alibaba.fastjson2.JSON;
import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.controller.FavController;
import com.example.jpa_demo.service.FavoriteServiceImpl;
import com.example.jpa_demo.service.MovieServiceImpl;
import com.example.jpa_demo.vo.CommentVO;
import com.example.jpa_demo.vo.FavoriteVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @projectName: apidesign
 * @package: com.example.jpa_demo
 * @className: FavControllerTeset
 * @author: Dushimao
 * @description: TODO
 * @date: 2023/6/23 15:06
 * @version: 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
@AutoConfigureMockMvc
public class FavControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private String token;
    @Mock
    private FavoriteServiceImpl favoriteService;
    @Mock
    private MovieServiceImpl movieService;
    @InjectMocks
    private FavController favController;

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
    public void testFavoriteListNormal() throws Exception{
        //TODO:测试正常获取收藏列表
        //perform
        MvcResult result=mockMvc.perform(get("/api/favorites")
                        .header("Authorization", "Bearer "+token)
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
    public void testDeleteFavNormal() throws Exception{
        //TODO:测试正常删除收藏
        //perform
        MvcResult result=mockMvc.perform(delete("/api/favorites/{id}", 6)
                        .header("Authorization", "Bearer "+token)
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
    public void testDeleteFavOtherUsers() throws Exception{
        //TODO:测试删除其他用户的收藏
        MvcResult result=mockMvc.perform(delete("/api/favorites/{id}", 1)
                        .header("Authorization", "Bearer "+token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Integer code = baseResponse.getCode();
        Assertions.assertEquals((long)10000, (long)code);
    }

    @Test
    public void testDeleteFavNotExistFav() throws Exception{
        //TODO:测试删除不存在的收藏
        MvcResult result=mockMvc.perform(delete("/api/favorites/{id}", 100000)
                        .header("Authorization", "Bearer "+token)
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
    public void testAddFavNormal() throws Exception{
        //TODO:测试正常增加收藏
        FavoriteVO favoriteVO=new FavoriteVO();
        favoriteVO.setMovieId(24);
        //perform
        MvcResult result=mockMvc.perform(post("/api/favorites")
                        .header("Authorization", "Bearer "+token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(favoriteVO)))
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
    public void testAddFavNotExistMovie() throws Exception{
        //TODO:测试增加不存在电影的收藏
        FavoriteVO favoriteVO=new FavoriteVO();
        favoriteVO.setMovieId(1);
        //perform
        MvcResult result=mockMvc.perform(post("/api/favorites")
                        .header("Authorization", "Bearer "+token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(favoriteVO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Integer code = baseResponse.getCode();
        Assertions.assertEquals((long)10003, (long)code);
    }

    @Test
    public void testAddFavExistFav() throws Exception{
        //TODO:测试增加已经收藏的电影
        FavoriteVO favoriteVO=new FavoriteVO();
        favoriteVO.setMovieId(5);
        //perform
        MvcResult result=mockMvc.perform(post("/api/favorites")
                        .header("Authorization", "Bearer "+token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(favoriteVO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Integer code = baseResponse.getCode();
        Assertions.assertEquals((long)10002, (long)code);
    }
}
