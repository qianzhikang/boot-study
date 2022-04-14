package com.qzk.boot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Date 2022-04-12-19-08
 * @Author Courage
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class EmailServiceTest {
    @Resource
    private EmailService emailService;

    @Test
    void mailTest() {
        emailService.sendSimpleMail("957463620@qq.com", "From qzk的测试邮件", "内容测试");
    }

    @Test
    void sendHtmlMail() throws MessagingException {
        emailService.sendHtmlMail("957463620@qq.com", "From qzk的测试邮件", "<h1>这是一段html测试内容</h1>");
    }

}