package com.qzk.boot.controller;
import com.qzk.boot.entity.Article;
import com.qzk.boot.service.ExceptionService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * @Description TODO
 * @Date 2022-04-11-12-22
 * @Author Courage
 */
@RestController
public class ErrorController {
    @Resource
    private ExceptionService exceptionService;

    @GetMapping("/test/{id}")
    public Article test(@PathVariable("id") int id) {
        if (id == 0) {
            exceptionService.systemError();
        }
        exceptionService.userError(id);
        Article article = Article.builder().id(id).title("springboot").build();
        return article;
    }

    @PostMapping("/article")
    public Article saveArticle(@Valid @RequestBody Article article) {
        return article;
    }
}
