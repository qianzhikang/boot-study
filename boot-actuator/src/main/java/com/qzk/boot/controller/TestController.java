package com.qzk.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Date 2022-04-15-09-24
 * @Author Courage
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World";
    }
}
