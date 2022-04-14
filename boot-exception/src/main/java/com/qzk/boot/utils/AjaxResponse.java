package com.qzk.boot.utils;

import com.qzk.boot.exception.CustomException;
import lombok.Data;

/**
 * @Description TODO
 * @Date 2022-04-11-11-54
 * @Author Courage
 */
@Data
public class AjaxResponse {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;

    private AjaxResponse() {

    }

    /**
     * 请求成功的响应，不带返回参数
     * @return AjaxResponse
     */
    public static AjaxResponse success() {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setCode(200);
        ajaxResponse.setMessage("请求响应成功");
        return ajaxResponse;
    }

    /**
     * 带返回值的响应成功
     * @param data 返回数据
     * @return AjaxResponse
     */
    public static AjaxResponse success(Object data) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setCode(200);
        ajaxResponse.setData(data);
        ajaxResponse.setMessage("请求响应成功");
        return ajaxResponse;
    }

    /**
     * 请求异常
     * @param e 异常
     * @return AjaxResponse
     */
    public static AjaxResponse error(CustomException e) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setCode(e.getCode());
        ajaxResponse.setMessage(e.getMessage());
        return ajaxResponse;
    }

    /**
     * 请求异常重载
     * @param e 异常
     * @param message 自定义异常信息
     * @return AjaxResponse
     */
    public static AjaxResponse error(CustomException e,String message) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setCode(e.getCode());
        ajaxResponse.setMessage(message);
        return ajaxResponse;
    }
}
