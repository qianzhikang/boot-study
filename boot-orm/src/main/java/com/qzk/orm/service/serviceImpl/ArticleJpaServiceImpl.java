package com.qzk.orm.service.serviceImpl;

import com.qzk.orm.dao.ArticleRepository;
import com.qzk.orm.entity.Article;
import com.qzk.orm.service.ArticleJpaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Date 2022-03-18-15-01
 * @Author Courage
 */
@Service
public class ArticleJpaServiceImpl implements ArticleJpaService {

    @Resource
    private ArticleRepository articleRepository;


    @Override
    public List<Article> findArticleByAuthor(String author) {
        return articleRepository.findByAuthor(author);
    }
}
