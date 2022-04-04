package com.qzk.interceptor.springinterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Date 2022-04-04-14-57
 * @Author Courage
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Resource
    CustomHandlerInterceptor customHandlerInterceptor;

    @Resource
    AccessLogInterceptor accessLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customHandlerInterceptor).addPathPatterns("/*");
        registry.addInterceptor(accessLogInterceptor).addPathPatterns("/*");
    }
}
