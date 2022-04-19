package com.qzk.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Courage
 */
@SpringBootApplication
@EnableCaching
public class BootCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootCacheApplication.class, args);
    }

}
