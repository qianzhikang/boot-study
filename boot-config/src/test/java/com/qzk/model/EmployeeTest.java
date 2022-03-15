package com.qzk.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Date 2022-03-14-15-47
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class) //junit5 写法
//@RunWith(SpringRunner.class) junit4 写法
class EmployeeTest {
    @Resource
    private Employee employee;


    @Test
    void valueBind(){
        System.out.println(employee);
    }
}