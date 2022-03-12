package com.qzk.boot.restful.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @Description TODO
 * @Date 2022-03-12-19-12
 * @Author Courage
 */
@Slf4j
class ArticleControllerTest {

    private static MockMvc mockMvc;


    @BeforeAll
    static void setUp() {
        //在所有测试⽅法执⾏之前进⾏mock对象初始化
        mockMvc = MockMvcBuilders.standaloneSetup(new
                ArticleController()).build();
    }

    @Test
    void saveArticle() throws Exception {
        //""" 是jdk15新特性，代表一个文本块。
        String article = """
               {
                    "id": 1,
                    "writer": "qzk",
                    "title": "SpringBoot",
                    "content": "SpringBoot",
                    "createTime": "2022-03-12 11:11:11",
                    "readerList":[{"name":"qzk","age":18},
                    {"name":"cw","age":20}]
               }""";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/api/v1/articles/body")
                        .contentType("application/json").content(article))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.writer").value("qzk"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.readerList[0].age").value(18))
                .andDo(print())
                .andReturn();
        result.getResponse().setCharacterEncoding("UTF-8");
        log.info(result.getResponse().getContentAsString());
    }



}