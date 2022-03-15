package com.qzk.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Date 2022-03-14-11-12
 * @Author Courage
 */
//模拟spring容器启动需要以下注解
@SpringBootTest
@ExtendWith(SpringExtension.class)
class FamilyTest {
    //自动注入springboot托管的java类
    @Autowired
    private Family family;

    @Test
    public void print(){
        System.out.println(family);
    }
}