package com.qzk.interceptor;

import com.qzk.interceptor.event.MyEvent;
import com.qzk.interceptor.listener.MyListener1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Courage
 */
@SpringBootApplication
//@ServletComponentScan
public class BootInterceptorApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context  = SpringApplication.run(BootInterceptorApplication.class, args);
        //添加事件监听
        context.addApplicationListener(new MyListener1());
        //发布事件
        context.publishEvent(new MyEvent("测试事件"));
    }

}
