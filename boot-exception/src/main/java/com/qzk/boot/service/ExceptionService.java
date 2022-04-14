package com.qzk.boot.service;

import com.qzk.boot.consts.MsgConsts;
import com.qzk.boot.enums.CustomExceptionType;
import com.qzk.boot.exception.CustomException;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Date 2022-04-11-12-01
 * @Author Courage
 */
@Service
public class ExceptionService {

    /**
     * 系统异常
     */
    public void systemError(){
        try {
            Class.forName("com.mysql.jdbc.cj.Driver");
        } catch (ClassNotFoundException e) {
            throw new CustomException(CustomExceptionType.SYSTEM_ERROR,
                    MsgConsts.SYSTEM_ERRPR_Message);
        }
    }


    public void userError(int input){
        if (input < 0){
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,"输入的数据不符合要求");
        }
    }
}
