package com.qzk.interceptor.listener;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @Description TODO
 * @Date 2022-04-04-10-40
 * @Author Courage
 */
//@WebListener
public class CustomListener implements ServletContextListener,
        HttpSessionListener, ServletRequestListener,
        ServletRequestAttributeListener, HttpSessionAttributeListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("context容器初始化");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("context容器销毁");
    }

    @Override
    public void attributeAdded(ServletRequestAttributeEvent srae) {
        if ("a".equals(srae.getName())) {
            System.out.println("a添加进了requestAttribute");
        }
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent srae) {
        if ("a".equals(srae.getName())) {
            System.out.println("a移除了requestAttribute");
        }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("request销毁了");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("request初始化");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("session创建");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("session销毁了");
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        if ("a".equals(se.getName())) {
            System.out.println("a添加了sessionAttribute");
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        if ("a".equals(se.getName())) {
            System.out.println("a移除了sessionAttribute");
        }
    }
}
