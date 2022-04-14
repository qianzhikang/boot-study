package com.qzk.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description TODO
 * @Date 2022-04-12-21-03
 * @Author Courage
 */
@Component
public class TaskConfiguration {

    @Bean("taskExecutor")
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(16);
        executor.setKeepAliveSeconds(60);
        executor.setAllowCoreThreadTimeOut(true);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("taskExecutor");
        return executor;
    }
}
