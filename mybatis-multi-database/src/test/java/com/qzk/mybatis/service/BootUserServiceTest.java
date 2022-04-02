package com.qzk.mybatis.service;

import com.qzk.mybatis.entity.BootUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Date 2022-03-31-22-08
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class BootUserServiceTest {

    @Resource
    private BootUserService bootUserService;


    @Test
    void getUsers() {
        List<BootUser> users = bootUserService.getUsers();
        users.forEach(user -> System.out.println(user));
    }

    @Test
    void saveUser(){
        BootUser bootUser = BootUser.builder().name("测试用户").detail("用户详情").build();
        bootUserService.saveUser(bootUser);
    }
}