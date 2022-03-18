package com.qzk.orm.controller;

import com.qzk.orm.common.AjaxResponse;
import com.qzk.orm.entity.Article;
import com.qzk.orm.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Date 2022-03-18-10-21
 * @Author Courage
 */
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    @Resource
    ArticleService articleService;

    @GetMapping("/all")
    public AjaxResponse selectAll() {
        List<Article> articles = articleService.selectAllArticle();
        if (articles != null) {
            return AjaxResponse.success(articles);
        } else {
            return AjaxResponse.failed();
        }

    }

    @GetMapping("/{id}")
    public AjaxResponse findById(@PathVariable("id") int id) {
        Article article = articleService.findArticleById(id);
        if (article != null) {
            return AjaxResponse.success(article);
        }
        return AjaxResponse.failed();
    }


    @DeleteMapping("/delete")
    public AjaxResponse deleteById(@RequestParam("id") int id) {
        int result = articleService.deleteArticleById(id);
        if (result != 0) {
            return AjaxResponse.success();
        }
        return AjaxResponse.failed();
    }


    @PostMapping("/save")
    public AjaxResponse addArticle(@RequestBody Article article) {
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        int result = articleService.addArticle(article);
        if (result != 0) {
            return AjaxResponse.success();
        } else {
            return AjaxResponse.failed();
        }
    }


    @PostMapping("/update")
    public AjaxResponse updateArticle(@RequestBody Article article) {
        article.setUpdateTime(new Date());
        int result = articleService.updateArticleById(article);
        if (result != 0) {
            return AjaxResponse.success();
        } else {
            return AjaxResponse.failed();
        }
    }

}
