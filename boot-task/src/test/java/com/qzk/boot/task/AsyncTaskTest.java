package com.qzk.boot.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Date 2022-04-12-15-44
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class AsyncTaskTest {
    @Resource
    AsyncTask asyncTask;
    @Test
    void doTaskAsync() throws Exception {
        asyncTask.doTaskOne();
        asyncTask.doTaskTwo();
        asyncTask.doTaskThree();
    }
}