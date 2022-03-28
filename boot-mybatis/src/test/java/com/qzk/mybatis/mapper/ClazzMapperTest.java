package com.qzk.mybatis.mapper;

import com.qzk.mybatis.entity.Clazz;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Date 2022-03-28-17-25
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ClazzMapperTest {

    @Resource
    private ClazzMapper clazzMapper;

    @Test
    void findOneByMany(){
        Clazz clazz = clazzMapper.findOneByMany(1);
        clazz.getStudents().forEach(student -> System.out.println(student));
    }

    @Test
    void getClazz(){
        Clazz clazz = clazzMapper.getClazz(1);
        clazz.getStudents().forEach(student -> {
            System.out.println(student);
        });
        System.out.println();
    }

}