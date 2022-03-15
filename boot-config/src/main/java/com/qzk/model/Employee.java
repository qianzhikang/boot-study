package com.qzk.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Date 2022-03-14-15-39
 * @Author Courage
 */
@Data
//表示本类是一个配置类
@Configuration
@PropertySource(value = {"classpath:employee.properties"},name = "employeeProperties")
public class Employee {

    /**
     * 使用SpEL来读取配置文件
     */
    @Value("#{'${employee.names}'.split(',')}")
    private List<String> employeeNames;
    @Value("#{'${employee.names}'.split(',')[0]}")
    private String firstNames;

    @Value("#{${employee.age}}")
    private Map<String, Integer> employeeAge;

    @Value("#{${employee.age}['one'] != null ? ${employee.age}['one']: 999}")
    private Integer ageWithDefaultValue;

    @Value("#{systemProperties['java.home']}")
    private String javaHome;

    @Value("#{systemProperties['user.dir']}")
    private String userDir;

}
