package com.qzk.boot.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Date 2022-04-12-21-55
 * @Author Courage
 */
@Configuration
public class QuartzSimpleConfig {
    @Bean
    public JobDetail uploadTaskDetail() {
        return JobBuilder.newJob(QuartzSimpleTask.class)
                .withIdentity("QuartzSimpleTask")
                .storeDurably()
                .build();
    }
    @Bean
    public Trigger uploadTaskTrigger() {
        //这⾥设定触发执⾏的⽅式
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("*/5 * * * * ?");
        // 返回任务触发器
        return TriggerBuilder.newTrigger().forJob(uploadTaskDetail())
                .withIdentity("QuartzSimpleTask")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
