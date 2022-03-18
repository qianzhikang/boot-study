package com.qzk.orm.service.serviceImpl;

import com.qzk.orm.dao.ArticleDao;
import com.qzk.orm.entity.Article;
import com.qzk.orm.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 文章服务实现类
 * @Date 2022-03-18-10-11
 * @Author Courage
 */

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    ArticleDao articleDao;


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int addArticle(Article article) {
        return articleDao.save(article);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int deleteArticleById(int id) {
        return articleDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int updateArticleById(Article article) {
        return articleDao.updateById(article);
    }

    @Override
    public Article findArticleById(int id) {
        return articleDao.findById(id);
    }

    @Override
    public List<Article> selectAllArticle() {
        return articleDao.selectAll();
    }

}
