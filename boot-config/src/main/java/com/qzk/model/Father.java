package com.qzk.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

/**
 * @Description TODO
 * @Date 2022-03-14-11-07
 * @Author Courage
 */
@Data
public class Father {
    private String name;
    @Min(value = 22,message = "爸爸的年龄不能小于22随")
    private Integer age;
    @Email
    private String email;
}
