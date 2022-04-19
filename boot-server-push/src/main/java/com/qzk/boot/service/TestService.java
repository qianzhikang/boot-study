package com.qzk.boot.service;

import com.qzk.boot.socket.ScheduledWebSocket;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Date 2022-04-18-14-42
 * @Author Courage
 */
@Service
public class TestService {
    @Resource
    ScheduledWebSocket scheduledWebSocket;

    //@Scheduled(cron = "0/3 * * * * ?")
    public void realTimeAlarm(){
        scheduledWebSocket.sendMessageById(1,"<h3>您追的xxx更新了！</h3>\n" +
                "<img src=\"https://pic-go.oss-cn-shanghai.aliyuncs.com/typora-img/202204181521041.jpg\" alt=\"\" style=\"width: 100px; height: 100px;\">");
    }
}
