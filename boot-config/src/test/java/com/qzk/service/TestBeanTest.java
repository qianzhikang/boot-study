package com.qzk.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Date 2022-03-14-15-11
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class TestBeanTest {

    @Resource
    ConfigurableApplicationContext ioc;

    @Test
    void test(){
        TestBean testBean = (TestBean) ioc.getBean("testBean");
        TestBean testBean1 = (TestBean) ioc.getBean("testBean1");
        System.out.println(testBean.getName());
        System.out.println(testBean1.getName());

    }
}