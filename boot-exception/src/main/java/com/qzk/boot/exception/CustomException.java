package com.qzk.boot.exception;

import com.qzk.boot.enums.CustomExceptionType;

/**
 * @Description TODO
 * @Date 2022-04-11-11-42
 * @Author Courage
 */
public class CustomException extends RuntimeException{

    /**
     * 状态码
     */
    private int code;

    /**
     * 错误信息
     */
    private String message;

    private CustomException(){

    }

    public CustomException(CustomExceptionType exceptionType){
        this.code = exceptionType.getCode();
        this.message = exceptionType.getDesc();
    }

    public CustomException(CustomExceptionType exceptionType,String message){
        this.code = exceptionType.getCode();
        this.message = message;
    }

    public int getCode(){
        return code;
    }
    @Override
    public String getMessage(){
        return message;
    }

}
