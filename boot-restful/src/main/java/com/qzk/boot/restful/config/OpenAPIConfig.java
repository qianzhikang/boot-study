package com.qzk.boot.restful.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Date 2022-03-12-20-26
 * @Author Courage
 */
@Configuration
public class OpenAPIConfig {
    @Bean
    public GroupedOpenApi articleAPI(){
        return GroupedOpenApi.builder().group("api-v1-article").pathsToMatch("/api/v1/articles/**").build();
    }

    @Bean
    public GroupedOpenApi helloAPI(){
        return GroupedOpenApi.builder().group("hello").pathsToMatch("/hello/**").build();
    }

}
