package com.qzk.boot.handler;

import com.qzk.boot.enums.CustomExceptionType;
import com.qzk.boot.exception.CustomException;
import com.qzk.boot.utils.AjaxResponse;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;

/**
 * @Description TODO
 * @Date 2022-04-11-12-09
 * @Author Courage
 */
@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public AjaxResponse customException(CustomException e){
        if (e.getCode() == CustomExceptionType.SYSTEM_ERROR.getCode()){
            //todo 500异常持久化
        }
        return AjaxResponse.error(e);
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public AjaxResponse exception(Exception e){
        return AjaxResponse.error(new CustomException(CustomExceptionType.OTHER_ERROR));
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public AjaxResponse handleBindException(MethodArgumentNotValidException e){
        FieldError fieldError = e.getBindingResult().getFieldError();
        assert fieldError != null;
        return AjaxResponse.error(new CustomException(CustomExceptionType.USER_INPUT_ERROR,fieldError.getDefaultMessage()));
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public AjaxResponse handIllegalArgumentException(IllegalAccessException e){
        return AjaxResponse.error(new CustomException(CustomExceptionType.USER_INPUT_ERROR,e.getMessage()));
    }














}
