package com.qzk.model;

import jdk.swing.interop.SwingInterOpUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Date 2022-03-14-14-24
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserTest {

    @Autowired
    User user;

    @Test
    public void print(){
        System.out.println(user);
    }

}