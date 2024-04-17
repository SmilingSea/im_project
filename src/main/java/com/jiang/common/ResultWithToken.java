package com.jiang.common;


import lombok.Data;


/**
 * 通用返回结果类，返回信息和token
 * @author SmilingSea
 * @param <T>
 */

@Data
public class ResultWithToken<T> {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 信息
     */
    private String message;

    /**
     * 数据
     */
    private T token;


    /**
     * 构造方法
     * @param token  传入参数
     * @return result
     * @param <T> 类型
     */
    public static <T> ResultWithToken<T> success(T token) {
        ResultWithToken<T> r = new ResultWithToken<>();
        r.success = true;
        r.token = token;
        return r;
    }
    /**
     * 构造方法
     * @param token  传入参数
     * @return result
     * @param <T> 类型
     */
    public static <T> ResultWithToken<T> success(T token, String msg) {
        ResultWithToken<T> r = new ResultWithToken<>();
        r.success = true;
        r.message = msg;
        r.token = token;
        return r;
    }

    /**
     * 构造方法）成功
     * @param msg 传入参数
     * @return
     * @param <T> 类型
     */
    public static <T> ResultWithToken<T> success(String msg){
        ResultWithToken<T> r = new ResultWithToken<>();
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
    public static <T> ResultWithToken<T> error(String msg) {
        ResultWithToken r = new ResultWithToken();
        r.message = msg;
        r.success = false;
        return r;
    }


}
