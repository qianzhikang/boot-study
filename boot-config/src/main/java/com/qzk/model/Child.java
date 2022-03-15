package com.qzk.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @Description TODO
 * @Date 2022-03-14-11-06
 * @Author Courage
 */
@Data
public class Child {
    private String name;
    private Integer age;
    private List<Friend> friends;
}
