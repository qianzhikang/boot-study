package com.qzk.interceptor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description TODO
 * @Date 2022-04-04-14-28
 * @Author Courage
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessLog {
    private String ip;
    private String url;
    private String username;
    private Integer duration;
    private Date createTime;
    private Integer httpStatus;
    private String httpMethod;

    @Override
    public String toString() {
        return "ip = " + ip + " , " + "url = " + url + " , " + "username = " + username+ " , " + "duration = " + duration+ " , " + "createTime = " + createTime+ " , " + "httpStatus = "
                + httpStatus + " , "+ "method = " + httpMethod;
    }
}
