package com.qzk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @Description TODO
 * @Date 2022-03-14-10-57
 * @Author Courage
 */
@SpringBootApplication
@ImportResource(value = "classpath:bean.xml")
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class,args);
    }
}
