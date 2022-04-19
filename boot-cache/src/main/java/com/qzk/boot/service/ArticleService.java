package com.qzk.boot.service;

import com.qzk.boot.entity.Article;

/**
 * @Description TODO
 * @Date 2022-04-17-21-09
 * @Author Courage
 */
public interface ArticleService {
    /**
     * 根据id查询文章
     * @param id 文章id
     * @return 文档对象
     */
    Article findById(int id);
}
