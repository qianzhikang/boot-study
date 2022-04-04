package com.qzk.interceptor.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Date 2022-04-04-13-15
 * @Author Courage
 */
//@Configuration
public class FilterRegistration {

    @Bean
    public FilterRegistrationBean filterRegistrationBean1(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new CustomFilter());
        registrationBean.setName("customFilter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(10);
        return  registrationBean;
    }


    @Bean
    public FilterRegistrationBean filterRegistrationBean2(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new MyFilter());
        registrationBean.setName("myFilter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(5);
        return  registrationBean;
    }
}
