package com.shine.goods.controller;

import com.shine.common.ServerResponse;
import com.shine.common.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/***********
 *@Author: Shine
 *@Description: 全局异常捕获
 *@Data: Created in 2019/12/27 0:26
 *@Modified By:
 *****/
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ServerResponse error(Exception e){
        e.printStackTrace();
        return ServerResponse.errorCodeMessage(StatusCode.ERROR, e.getMessage());
    }

}
