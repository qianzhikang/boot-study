package com.qzk.boot.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import java.util.concurrent.Future;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Date 2022-04-12-21-18
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class AsyncExecutorTaskTest {

    @Resource
    AsyncExecutorTask asyncExecutorTask;
    @Test
    void doTask() throws Exception {
        Future<String> task1 = asyncExecutorTask.doTaskOneCallback();
        Future<String> task2 = asyncExecutorTask.doTaskTwoCallback();
        Future<String> task3 = asyncExecutorTask.doTaskThreeCallback();

        while(!task1.isDone()||!task2.isDone()||!task3.isDone()){
            sleep(1000);
        }

    }
}