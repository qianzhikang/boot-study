package com.qzk.interceptor.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Description TODO
 * @Date 2022-04-04-13-12
 * @Author Courage
 */
//@WebFilter(filterName = "myFilter",urlPatterns = {"/*"})
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("myFilter初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("请求处理之前----myFilter之前过滤的请求");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("请求处理之后----myFilter之后处理响应");
    }

    @Override
    public void destroy() {
        System.out.println("myFilter销毁");
    }
}
