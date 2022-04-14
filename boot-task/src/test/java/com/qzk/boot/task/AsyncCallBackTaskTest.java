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
 * @Date 2022-04-12-20-50
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class AsyncCallBackTaskTest {

    @Resource
    AsyncCallBackTask asyncCallBackTask;

    @Test
    void doTaskOneCallback() throws Exception {
        Future<String> stringFuture1 = asyncCallBackTask.doTaskOneCallback();
        Future<String> stringFuture2 = asyncCallBackTask.doTaskTwoCallback();
        Future<String> stringFuture3 = asyncCallBackTask.doTaskThreeCallback();

        while(!stringFuture1.isDone()||!stringFuture2.isDone()||!stringFuture3.isDone()){
            sleep(1000);
        }
    }
}