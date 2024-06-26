package com.jiang.common;

/**
 * 通用返回结果类
 * @author SmilingSea
 * 2024/3/31
 */


import lombok.Data;


@Data
public class ResultWithData<T> {

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
    private T data;


    /**
     * 构造方法
     *
     * @param <T>  类型
     * @param data 传入参数
     * @param 查找成功
     * @return result
     */
    public static <T> ResultWithData<T> success(T data, String msg) {
        ResultWithData<T> r = new ResultWithData<>();
        r.success = true;
        r.data = data;
        r.message = msg;
        return r;
    }
    /**
     * 构造方法）成功
     * @param msg 传入参数
     * @return
     * @param <T> 类型
     */
    public static <T> ResultWithData<T> success(String msg){
        ResultWithData<T> r = new ResultWithData<>();
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
    public static <T> ResultWithData<T> error(String msg) {
        ResultWithData r = new ResultWithData();
        r.message = msg;
        r.success = false;
        return r;
    }


}

