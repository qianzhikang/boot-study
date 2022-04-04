package com.qzk.interceptor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description TODO
 * @Date 2022-04-04-10-50
 * @Author Courage
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(HttpSession session,HttpServletRequest httpServletRequest){
        session.setAttribute("a","a");
        session.removeAttribute("a");
        httpServletRequest.setAttribute("a","a");
        httpServletRequest.removeAttribute("a");
        return "hello";
    }


    @GetMapping("/go")
    public String go(HttpSession session,HttpServletRequest httpServletRequest){
        return "hello";
    }
}
