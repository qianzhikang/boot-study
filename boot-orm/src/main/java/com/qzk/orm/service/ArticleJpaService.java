package com.qzk.orm.service;

import com.qzk.orm.entity.Article;

import java.util.List;

/**
 * @Description TODO
 * @Date 2022-03-18-15-01
 * @Author Courage
 */
public interface ArticleJpaService {
    /**
     * 根据作者查文章
     * @param author 入参：作者
     * @return 作者的文章列表
     */
    List<Article> findArticleByAuthor(String author);
}
