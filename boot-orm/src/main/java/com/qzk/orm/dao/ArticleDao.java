package com.qzk.orm.dao;

import com.qzk.orm.entity.Article;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Date 2022-03-18-08-49
 * @Author Courage
 */
@Repository
public class ArticleDao {

    @Resource
    private JdbcTemplate jdbcTemplate;


    /**
     * 添加文章
     * @param article 入参：文章实体类
     * @return 受影响行数
     */
    public int save(Article article){
        String sql = "INSERT INTO article (author,title,content,create_time,update_time,thumbnail) values (?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,
                article.getAuthor(),
                article.getTitle(),
                article.getContent(),
                article.getCreateTime(),
                article.getUpdateTime(),
                article.getThumbnail());
    }

    /**
     * 按id删除文章
     * @param id 入参：文章id
     * @return 受影响行数
     */
    public int deleteById(int id){
        String sql = "DELETE FROM article WHERE id = ?";
        return jdbcTemplate.update(sql,id);
    }

    /**
     * 按照id更新文章
     * @param article 入参：文章实体类
     * @return 受影响行数
     */
    public int updateById(Article article){
        String sql = "UPDATE article SET author = ?,title = ?,content = ?,create_time = ?,update_time = ?,thumbnail=? WHERE id = ?";
        return jdbcTemplate.update(sql,
                article.getAuthor(),
                article.getTitle(),
                article.getContent(),
                article.getCreateTime(),
                article.getUpdateTime(),
                article.getThumbnail(),
                article.getId());
    }

    /**
     * 根据id查找文章
     * @param id 入参：文章id
     * @return 文章实体类
     */
    public Article findById(int id){
        String sql = "SELECT * FROM article WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Article.class), new Object[]{id});
    }

    /**
     * 查找所有文章
     * @return 所有文章类的list
     */
    public List<Article> selectAll(){
        String sql = "SELECT * FROM article";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Article.class));
    }

}
