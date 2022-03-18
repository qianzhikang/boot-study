package com.qzk.orm.service;

import com.qzk.orm.entity.Article;

import java.util.List;

/**
 * @Description 文章服务接口
 * @Date 2022-03-18-10-09
 * @Author Courage
 */
public interface ArticleService {
    /**
     * 添加文章
     * @param article 入参：文章实体类
     * @return 受影响行数
     */
    int addArticle(Article article);

    /**
     * 按id删除文章
     * @param id 入参：文章id
     * @return  受影响行数
     */
    int deleteArticleById(int id);


    /**
     * 按照id更新文章
     * @param article 入参：文章实体类
     * @return 受影响行数
     */
    int updateArticleById(Article article);


    /**
     * 根据id查找文章
     * @param id 入参：文章id
     * @return 文章实体类
     */
    Article findArticleById(int id);


    /**
     * 查找所有文章
     * @return 所有文章类的list
     */
    List<Article> selectAllArticle();



}
