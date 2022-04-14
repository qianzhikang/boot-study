package com.qzk.boot.advice;

import com.qzk.boot.utils.AjaxResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.function.IntFunction;

/**
 * @Description TODO
 * @Date 2022-04-11-12-31
 * @Author Courage
 */
@Component
@ControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (selectedContentType.equalsTypeAndSubtype(MediaType.APPLICATION_JSON)) {
            if (body instanceof AjaxResponse ajaxResponse) {
                if (ajaxResponse.getCode() != 999) {
                    response.setStatusCode(HttpStatus.valueOf(ajaxResponse.getCode()));
                }
                return body;
            } else {
                response.setStatusCode(HttpStatus.OK);
                return AjaxResponse.success(body);
            }
        }
        return body;
    }
}
