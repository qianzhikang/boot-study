package com.qzk.boot.controller;

import com.qzk.boot.common.AjaxResponse;
import com.qzk.boot.service.ArticleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Date 2022-04-17-21-23
 * @Author Courage
 */
@RestController
public class ArticleController {

    @Resource
    ArticleService articleService;

    @PostMapping("/articles")
    public AjaxResponse findById(@RequestParam int id){
        return AjaxResponse.success(articleService.findById(id));
    }

}
