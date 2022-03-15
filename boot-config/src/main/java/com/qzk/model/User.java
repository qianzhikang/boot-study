package com.qzk.model;

import com.qzk.util.MixPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @Description TODO
 * @Date 2022-03-14-14-16
 * @Author Courage
 */

@Data
@Component
@ConfigurationProperties(prefix = "user")
@PropertySource(value = {"classpath:user.yml"},factory = MixPropertySourceFactory.class)
@Validated
public class User {
    private String name;
    private Book book;
}
