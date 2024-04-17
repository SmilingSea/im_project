package com.jiang.exception;

import com.jiang.common.Result;

import javax.annotation.Resource;

/**
 * 自定义没有token时的异常
 * @author SmilingSea
 * 2024/4/16
 */
public class NoTokenException extends RuntimeException {

    private boolean success;

    private String message;


    // 无参构造函数
    public NoTokenException() {
        super();
    }

    // 带有详细信息的构造函数
    public NoTokenException(String message) {
        super(message);
    }

    public NoTokenException(Result result){
        this.success = result.isSuccess();
        this.message = result.getMessage();
    }


    // 带有详细信息和原因的构造函数
    public NoTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
