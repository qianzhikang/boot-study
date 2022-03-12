package com.qzk.boot.restful.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Date 2022-03-12-20-23
 * @Author Courage
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello world,qzk!!";
    }
}
