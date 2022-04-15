package com.qzk.boot.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Date 2022-04-15-09-08
 * @Author Courage
 */
@Configuration
@Endpoint(id = "customEndpoint")
public class CustomEndpoint {
    @ReadOperation
    public ShopData getData(){
        return new ShopData("qzk","jiangsu");
    }

    @Data
    @AllArgsConstructor
    public static class ShopData{
        private String name;
        private String address;
    }
}

