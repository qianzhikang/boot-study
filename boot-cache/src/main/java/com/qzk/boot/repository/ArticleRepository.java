package com.qzk.boot.repository;

import com.qzk.boot.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description TODO
 * @Date 2022-04-17-17-08
 * @Author Courage
 */
public interface ArticleRepository extends JpaRepository<Article,Integer> {
}
