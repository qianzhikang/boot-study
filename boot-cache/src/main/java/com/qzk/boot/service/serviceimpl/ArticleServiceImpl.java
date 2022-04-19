package com.qzk.boot.service.serviceimpl;

import com.qzk.boot.entity.Article;
import com.qzk.boot.repository.ArticleRepository;
import com.qzk.boot.service.ArticleService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Description TODO
 * @Date 2022-04-17-21-14
 * @Author Courage
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private static final String CACHE_OBJECT = "article";

    @Resource
    ArticleRepository articleRepository;

    @Override
    @Cacheable(value = CACHE_OBJECT,key = "#id")
    public Article findById(int id) {
        System.out.println(id+" 执行一次");
        Optional<Article> op = articleRepository.findById(id);
        //优雅判空，若为空则返回括号中的值
        return op.orElse(null);
    }
}
