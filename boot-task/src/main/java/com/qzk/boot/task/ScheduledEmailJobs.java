package com.qzk.boot.task;

import com.qzk.boot.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Date 2022-04-12-19-37
 * @Author Courage
 */
//@Component
public class ScheduledEmailJobs {
    @Resource
    EmailService emailService;
    @Scheduled(cron = "0/10 * * * * ?")
    public void cronEmailJob(){
        System.out.println("每隔十秒执行一次");
    }
}
