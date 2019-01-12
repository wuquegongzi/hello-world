package com.leon.concurrency.annoations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标记不推荐的类和方法
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface NotRecommend {

    String value() default "";

}
