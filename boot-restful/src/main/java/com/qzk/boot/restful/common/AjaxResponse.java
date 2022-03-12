package com.qzk.boot.restful.common;

import lombok.Data;

/**
 * @Description 返回数据结构化
 * @Date 2022-03-10-16-39
 * @Author Courage
 */
@Data
public class AjaxResponse {
    private Integer code;
    private String message;
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
}
