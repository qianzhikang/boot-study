package com.qzk.interceptor.springinterceptor;

import com.qzk.interceptor.entity.AccessLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Description TODO
 * @Date 2022-04-04-14-27
 * @Author Courage
 */
@Component
@Slf4j
public class AccessLogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AccessLog accessLog = new AccessLog();
        accessLog.setIp(request.getRemoteAddr());
        accessLog.setHttpMethod(request.getMethod());
        accessLog.setUrl(request.getRequestURI());
        request.setAttribute("sendTime",System.currentTimeMillis());

        request.setAttribute("accessLog",accessLog);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        AccessLog accessLog = (AccessLog) request.getAttribute("accessLog");

        int status = response.getStatus();
        accessLog.setHttpStatus(status);

        accessLog.setUsername(request.getParameter("username"));

        Long currentTime = System.currentTimeMillis();

        Long sendTime = Long.parseLong(request.getAttribute("sendTime").toString());

        accessLog.setDuration(Integer.valueOf((currentTime-sendTime)+""));
        accessLog.setCreateTime(new Date());

        log.info(accessLog.toString());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("请求之后的处理。。。。");
    }
}
