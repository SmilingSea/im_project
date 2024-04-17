package com.jiang.handler;

import com.jiang.common.Result;
import com.jiang.exception.NoTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * 全局异常拦截器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoTokenException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> handleNoTokenException(NoTokenException e) {
        return Result.error("token为空");
    }
}