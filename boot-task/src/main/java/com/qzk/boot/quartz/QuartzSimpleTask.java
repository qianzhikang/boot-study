package com.qzk.boot.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description TODO
 * @Date 2022-04-12-21-54
 * @Author Courage
 */
public class QuartzSimpleTask extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        System.out.println("quartz简单的定时任务执⾏时间：" + new Date());
    }
}

