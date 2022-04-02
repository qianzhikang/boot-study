package com.qzk.orm.dao;

import com.qzk.orm.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * @Description TODO
 * @Date 2022-03-18-14-58
 * @Author Courage
 */
@RepositoryRestResource(path="articles")
public interface ArticleRepository extends JpaRepository<Article,Integer> {

    /**
     * 根据作者查找文章
     * @param author 入参：作者名
     * @return 文章list
     */
    List<Article> findByAuthor(String author);
}
