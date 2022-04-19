package com.qzk.boot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qzk.boot.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Date 2022-04-18-16-38
 * @Author Courage
 */
@Controller
@Slf4j
public class DemoController {

    @RequestMapping(value = "/server/info",method = {RequestMethod.GET},produces = "text/event-stream;charset=UTF-8")
    public ResponseBodyEmitter pushMsg(){
        SseEmitter emitter = new SseEmitter(0L);
        List<User> list = new ArrayList<>();
        User qzk = User.builder().name("qzk").avatar("https://pic-go.oss-cn-shanghai.aliyuncs.com/avatars/avatar04.jpg").build();
        User lmx = User.builder().name("qzk").avatar("https://pic-go.oss-cn-shanghai.aliyuncs.com/avatars/avatar01.jpg").build();
        list.add(qzk);
        list.add(lmx);
        ObjectMapper mapper = new ObjectMapper();
        try{
            String jsonStr = mapper.writeValueAsString(list);
            emitter.send(jsonStr, MediaType.TEXT_EVENT_STREAM);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return emitter;
    }
}
