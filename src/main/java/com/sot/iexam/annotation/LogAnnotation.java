package com.sot.iexam.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Kimbobo
 * @Date: 2019/12/9 12:09
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {
    /**
     * 对应方法的详细操作类型
     */
    String operateType();

    /**
     * 是否需要获取参数
     */
    boolean needData();

    /**
     * 如果needData为空，但是有特殊数据需要记录的话，记录
     */
  //  String[] specialData() default "";
}
