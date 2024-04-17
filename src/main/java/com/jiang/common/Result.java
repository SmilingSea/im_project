package com.jiang.common;


import lombok.Data;


/**
 * 通用返回结果类，服务端响应的数据最终都会封装成此对象
 *
 * @author SmilingSea
 * @param <T>
 */

@Data
public class Result<T> {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 信息
     */
    private String message;

    /**
     * 构造方法
     * @param object  传入参数
     * @return result
     * @param <T> 类型
     */
    public static <T> Result<T> success(T object) {
        Result<T> r = new Result<>();
        return r;
    }
    /**
     * 构造方法
     * @param object  传入参数
     * @return result
     * @param <T> 类型
     */
    public static <T> Result<T> success(T object, String msg) {
        Result<T> r = new Result<>();
        r.message = msg;
        return r;
    }

    /**
     * 构造方法）成功
     * @param msg 传入参数
     * @return
     * @param <T> 类型
     */
    public static <T> Result<T> success(String msg){
        Result<T> r = new Result<>();
        r.success = true;
        r.message = msg;
        return r;
    }


    /**
     * 构造方法
     * @param msg 错误信息
     * @return result
     * @param <T> 类型
     */
    public static <T> Result<T> error(String msg) {
        Result r = new Result();
        r.message = msg;
        r.success = false;
        return r;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
