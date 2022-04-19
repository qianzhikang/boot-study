# SpringBoot整合数据库开发

## 什么是数据的持久化？

将数据库保存到数据库、文件、磁盘等操作都称为数据持久化。

## 1. 整合Spring JDBC操作数据

### 0）准备数据库：

![image-20220320101514713](https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202203201015772.png)

### 1）添加依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

### 2）数据库连接配置：

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/spring_boot
    username: root
    password: 123456
```

### 3）实体类准备：

```java
package com.qzk.orm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description 文章实体类
 * @Date 2022-03-18-08-43
 * @Author Courage
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article implements Serializable {
  
    private Integer id;

    private String author;

    private String title;

    private String thumbnail;

    private String content;

    private Date createTime;

    private Date updateTime;
}
```

### 4）dao层业务编写：

```java
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
```

### 5）测试：

```java
package com.qzk.orm.dao;

import com.qzk.orm.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Date 2022-03-18-09-02
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
class ArticleDaoTest {


    @Resource
    ArticleDao articleDao;

    @Test
    void save() {
        int result = articleDao.save(Article.builder()
                .author("qzk")
                .content("mybatis入门到入土")
                .title("mybatis")
                .createTime(new Date())
                .build());
        assertEquals(1, result);
    }

    @Test
    void deleteById() {
        int i = articleDao.deleteById(2);
        assertEquals(1, i);
    }

    @Test
    void updateById() {
        int i = articleDao.updateById(Article.builder()
                .id(1)
                .author("aaa")
                .title("springboot")
                .content("我放弃了")
                .createTime(new Date())
                .build());
        assertEquals(1, i);
    }

    @Test
    void findById() {
        Article article = articleDao.findById(1);
        log.info(article.toString());
    }

    @Test
    void selectAll() {
        List<Article> articles = articleDao.selectAll();
        articles.forEach(article -> log.info(article.toString()));
    }
}
```





### 关于事务的回滚配置

当程序执行过程中，业务逻辑发生错误的时候，需要撤销之前的数据库更新操作，这个时候就需要事务的回滚，Spring JDBC在服务实现层提供了@Transactional(rollbackFor = {Exception.class})注解实现事务的回滚。

#### 1）业务接口

```java
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

```





#### 2）业务实现层：

- @Transactional(rollbackFor = {Exception.class})的使用

```java
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
```







## 2.整合SpringDataJPA

### 简介

SpringDataJPA是SpringData中的一个子模块，用于简化数据库访问。SpringDataJPA的核心接口就是Repository。只要我们的接口实现这个接口，那么我们就相当于在使用SpringDataJPA了。只要我们实现了这个接口，我们就可以使用"按照命名规则命名的方法"来进行查询,不需要写sql语句。

### 0）数据库使用上文创建的同表

### 1）添加依赖：

```xml
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

### 2）实体类准备：

- @Table(name = "article") 声明数据表

- @Entity 声明实体类
- @Id 声明主键
- @GeneratedValue(strategy = GenerationType.IDENTITY) 表示指定该列的自增方式
-  @Column(name = "create_time",updatable = false) 声明该列数据不更新

```java
package com.qzk.orm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description 文章实体类
 * @Date 2022-03-18-08-43
 * @Author Courage
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "article")
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String author;

    private String title;

    private String thumbnail;

    private String content;

    @Column(name = "create_time",updatable = false)
    private Date createTime;

    private Date updateTime;
}

```



### 3）Repository接口编写：

- 该类继承自 JpaRepository<>,用于实现基础的增删改查方法
- 这个接口内的方法名拥有定义查询的规则：如
  - findByAuthor(String author) 表示根据作者名查找

```java
package com.qzk.orm.dao;

import com.qzk.orm.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description TODO
 * @Date 2022-03-18-14-58
 * @Author Courage
 */
public interface ArticleRepository extends JpaRepository<Article,Integer> {

    /**
     * 根据作者查找文章
     * @param author 入参：作者名
     * @return 文章list
     */
    List<Article> findByAuthor(String author);
}
```



### 4) 测试类编写

```java
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
```



### 关于Repository接口中查询方法定义的命名规则

|      Keyword      |             Sample              |                            JPQL                             |
| :---------------: | :-----------------------------: | :---------------------------------------------------------: |
|        And        |   findByLastnameAndFirstname    |           where x.lastname=?1 and x.firstname=?2            |
|        Or         |    findByLastnameOrFirstname    |            where x.lastname=?1 or x.firstname=?2            |
|      Between      |     findByStartDateBetween      |             where x.startDate between ?1 and ?2             |
|     LessThan      |        findByAgeLessThan        |                   where x.startDate < ?1                    |
|    GreaterThan    |      findByAgeGreaterThan       |                    where x.startDate >?1                    |
|       After       |      findByStartDateAfter       |                   where x.startDate >n ?1                   |
|      Before       |      findByStartDateBefore      |                   where x.startDate < ?1                    |
|      IsNull       |         findByAgeIsNull         |                     where x.age is null                     |
| IsNotNull,NotNull |      findByAge(Is)NotNull       |                    where x.age not null                     |
|       Like        |       findByFirstnameLike       |                  where x.firstname like ?1                  |
|      notLike      |     findByFirstnameNotLike      |                where x.firstname not like ?1                |
|   StartingWith    | findByFirstnameStartingWithXXX  | where x.firstname like ?1(parameter bound  with appended %) |
|    EndingWith     |  findByFirstnameEndingWithXXX   | where x.firstname like ?1(parameter bound  with appended %) |
|    Containing     |    findByFirstnameContaining    |  where x.firstname like ?1(parameter bound  wrapped in %)   |
|      OrderBy      |    findByAgeOrderByLastname     |          where x.age = ?1 order by x.lastname desc          |
|        Not        |        findByLastnameNot        |                   where x.lastname <> ?1                    |
|       NotIn       | findByAgeNotIn(Collection age ) |                    where x.age not in ?1                    |
|       True        |       findByActiveTrue()        |                    where x.active = true                    |
|       False       |       findByActiveFalse()       |                   where x.active = false                    |

