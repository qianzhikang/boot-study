package com.qzk.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Courage
 */
@SpringBootApplication
@EnableScheduling
public class BootServerPushApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootServerPushApplication.class, args);
    }
}
