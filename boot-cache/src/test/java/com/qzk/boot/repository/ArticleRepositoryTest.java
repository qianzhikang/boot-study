package com.qzk.boot.repository;

import com.qzk.boot.entity.Article;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Date 2022-04-17-17-10
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ArticleRepositoryTest {

    @Resource
    ArticleRepository articleRepository;

    @Test
    void jpaTest(){
        articleRepository.save(Article.builder()
                .id(1)
                .title("spring")
                .author("qzk")
                .thumbnail("img")
                .content("开摆")
                .createTime(new Date())
                .updateTime(new Date())
                .build());
        List<Article> all = articleRepository.findAll();
        System.out.println(all);
    }
}