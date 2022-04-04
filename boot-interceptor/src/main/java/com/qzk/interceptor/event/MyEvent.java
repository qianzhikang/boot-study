package com.qzk.interceptor.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Description TODO
 * @Date 2022-04-04-15-07
 * @Author Courage
 */
public class MyEvent extends ApplicationEvent {
    public MyEvent(Object source)
    {
        super(source);
    }
}
