package com.tiger.functool.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2018/11/24.
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumName {
    String[] values() default "";  //Map中映射的key
    String value() default "";  //Map中映射的key
    String scala() default ""; //小数点后几位
    String formate() default "";//时间格式
    String unit() default ""; //单位名称
    String timeZone() default ""; //时区
    Class[] forbidClass() default {};//禁止转换的类
}
