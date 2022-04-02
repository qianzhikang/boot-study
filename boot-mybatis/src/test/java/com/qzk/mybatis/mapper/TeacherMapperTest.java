package com.qzk.mybatis.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;


/**
 * @Description TODO
 * @Date 2022-03-28-15-26
 * @Author Courage
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TeacherMapperTest {

    @Resource
    TeacherMapper teacherMapper;
    @Test
    public void test1(){
        System.out.println(teacherMapper.findOneByOne(1));
    }
}