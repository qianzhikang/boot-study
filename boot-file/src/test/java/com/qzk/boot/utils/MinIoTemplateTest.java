package com.qzk.boot.utils;

import io.minio.ObjectWriteResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Date 2022-04-14-16-46
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class MinIoTemplateTest {

    @Resource
    MinIoTemplate minIoTemplate;

    @Test
    void makeBucket() throws Exception {
        if (minIoTemplate.bucketExists("hello")){
            return;
        }else{
            minIoTemplate.makeBucket("hello");
            System.out.println("创建bucket成功");
        }
    }

    @Test
    void putObject() throws Exception {
        ObjectWriteResponse response = minIoTemplate.putObject("upload","avatar.jpg","C:\\Users\\Courage\\Pictures\\avatar\\avatar01.jpg");
        System.out.println(response.object());
    }

    @Test
    void testPutObject() {
    }

    @Test
    void removeObject() throws Exception {
        minIoTemplate.removeObject("upload","goodjob.jpg");
    }
}