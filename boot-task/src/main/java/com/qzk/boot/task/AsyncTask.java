package com.qzk.boot.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Date 2022-04-12-15-36
 * @Author Courage
 */
@Component
public class AsyncTask extends AbstractTask{
    @Override
    @Async //异步注解，不允许加在static方法上面
    public void doTaskOne() throws Exception {
        super.doTaskOne();
    }

    @Override
    @Async
    public void doTaskTwo() throws Exception {
        super.doTaskTwo();
    }

    @Override
    @Async
    public void doTaskThree() throws Exception {
        super.doTaskThree();
    }
}
