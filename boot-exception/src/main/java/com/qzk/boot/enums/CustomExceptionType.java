package com.qzk.boot.enums;

/**
 * @Description TODO
 * @Date 2022-04-11-11-29
 * @Author Courage
 */
public enum CustomExceptionType {


    /**
     * 用户输入异常
     */
    USER_INPUT_ERROR(400,"您输入的数据错误！"),


    /**
     * 系统错误
     */
    SYSTEM_ERROR(500,"系统发生错误"),


    /**
     * 系统崩溃了
     */
    OTHER_ERROR(999,"系统崩溃了");

    CustomExceptionType(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    /**
     * 状态码
     */
    private final int code;
    /**
     * 描述
     */
    private final String desc;

    public int getCode(){
        return code;
    }

    public String getDesc(){
        return desc;
    }


}
