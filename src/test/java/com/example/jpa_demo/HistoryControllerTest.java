package com.example.jpa_demo;

import com.example.jpa_demo.component.BaseResponse;
import com.example.jpa_demo.vo.HistoryVO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
@AutoConfigureMockMvc
public class HistoryControllerTest {
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

    // get history list
    @Test
    public void testGetHistoryList() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/histories")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(20000, baseResponse.getCode());
    }

    // get history list without token
    @Test
    public void testGetHistoryListWithoutToken() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/histories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        var response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(-1, baseResponse.getCode());
        String msg = baseResponse.getMsg();
        Assertions.assertEquals("invalid token", msg);
    }

    // get history list with invalid page
    @Test
    public void testGetHistoryListWithInvalidPage() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/histories?page=-1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        var response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(-1, baseResponse.getCode());
        var msg = objectMapper.readValue(objectMapper.writeValueAsString(baseResponse.getMsg()), String.class);
        Assertions.assertEquals("页码必须大于等于 0", msg);
    }

    // post history
    @Test
    public void testPostHistory() throws Exception {
        HistoryVO historyVO = new HistoryVO();
        historyVO.setMovieId(2);

        MvcResult result = mockMvc.perform(post("/api/histories")
                        .header("Authorization", "Bearer " + token)
                        .content(historyVO.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(20000, baseResponse.getCode());
    }

    // post history of non-exist movie
    @Test
    public void testPostHistoryNonExistMovie() throws Exception {
        HistoryVO historyVO = new HistoryVO();
        historyVO.setMovieId(1);

        MvcResult result = mockMvc.perform(post("/api/histories")
                        .header("Authorization", "Bearer " + token)
                        .content(historyVO.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(10001, baseResponse.getCode());
        String msg = baseResponse.getMsg();
        Assertions.assertEquals("电影不存在", msg);
    }

    // get history count
    @Test
    public void testGetHistoryCount() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/histories/count?movieId=2")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse baseResponse = objectMapper.readValue(response, BaseResponse.class);
        Assertions.assertEquals(20000, baseResponse.getCode());
        Assertions.assertNotNull(baseResponse.getData());
    }
}
