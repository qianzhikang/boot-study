package com.qzk.orm.dao;

import com.qzk.orm.entity.Article;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Date 2022-03-20-10-42
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ArticleRepositoryTest {

    @Resource
    ArticleRepository articleRepository;

    @Test
    void findByAuthor() {
        List<Article> article = articleRepository.findByAuthor("钱智康");
        assertEquals(1,article.size());


    }
}