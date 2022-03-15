package com.qzk.model;

import com.qzk.util.MixPropertySourceFactory;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @Description TODO
 * @Date 2022-03-14-11-08
 * @Author Courage
 */
@Data
//标注为组件
@Component
@ConfigurationProperties(prefix = "family")
//加载自定义的properties文件配置
@PropertySource(value = {"classpath:family.yml"},factory = MixPropertySourceFactory.class)
//校验
@Validated
public class Family {
    //获取配置文件中的内容
    //@Value("${family.family-name}")
    @Length(min = 5,max = 20,message = "家庭名称长度必须在5-20之间")
    private String familyName;
    private Father father;
    private Mother mother;
    private Child child;
}
