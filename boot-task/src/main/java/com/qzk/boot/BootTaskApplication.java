package com.qzk.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Courage
 */
@SpringBootApplication
@EnableAsync //开启异步任务
@EnableScheduling
public class BootTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootTaskApplication.class, args);
    }

}
