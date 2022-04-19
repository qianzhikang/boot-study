package com.qzk.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.server.ServerEndpoint;

/**
 * @Description WebSocket配置类
 * @Date 2022-04-18-10-24
 * @Author Courage
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        // 使用bean的方式注入开启 WebSocket
        return new ServerEndpointExporter();
    }
}
