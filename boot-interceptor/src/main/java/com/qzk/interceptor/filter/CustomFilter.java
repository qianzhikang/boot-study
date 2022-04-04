package com.qzk.interceptor.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Description TODO
 * @Date 2022-04-04-13-05
 * @Author Courage
 */
//@WebFilter(filterName = "customFilter", urlPatterns = {"/*"})
public class CustomFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("请求处理之前----CustomFilter之前过滤的请求");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("请求处理之后----CustomFilter之后处理响应");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("customFilter初始化");
    }

    @Override
    public void destroy() {
        System.out.println("customFilter销毁");
    }
}
