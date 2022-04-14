package com.qzk.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Date 2022-04-08-09-19
 * @Author Courage
 */
@RestController
public class Hello {

    @GetMapping("/hello")
    public String hello(){
        return "qzk";
    }
}
