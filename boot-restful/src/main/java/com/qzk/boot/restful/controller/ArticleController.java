package com.qzk.boot.restful.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qzk.boot.restful.common.AjaxResponse;
import com.qzk.boot.restful.model.Article;
import com.qzk.boot.restful.model.Reader;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Description TODO
 * @Date 2022-03-10-16-29
 * @Author Courage
 */

@RestController
@RequestMapping("/api/v1")
public class ArticleController {

    @GetMapping("/articles/{id}")
    public AjaxResponse getArticle(@PathVariable("id") Long id) {
        Article article = Article.builder()
                .id(id)
                .author("qzk")
                .tittle("spring")
                .content("why why why")
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        return AjaxResponse.success(article);
    }


    @PostMapping("/articles/body")
    public AjaxResponse saveArticle(@RequestBody Article article) {
        System.out.println("saveArticle:" + article);
        //System.out.println("name: " + name);
        return AjaxResponse.success(article);
    }


    //@PostMapping("/articles")
    //public AjaxResponse saveArticle(@RequestParam String author, @RequestParam String tittle, @RequestParam String content, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date createTime) {
    //    return AjaxResponse.success();
    //}


    @PutMapping("/articles")
    public AjaxResponse updateArticle(@RequestBody Article article) {
        System.out.println("updateArticle:" + article);
        return AjaxResponse.success(article);
    }


    @DeleteMapping("/articles")
    public AjaxResponse deleteArticle(@RequestParam Long id) {
        System.out.println("deleteArticle id:" + id);
        return AjaxResponse.success();
    }


}
