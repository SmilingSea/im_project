package com.jiang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义TokenRequired注解，用来检验token
 * @Target 元注解 这个注解指示了这个注解可以用在方法上
 * @Retention 标识注解的生命周期
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenRequired {
}
