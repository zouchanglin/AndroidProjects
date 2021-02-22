package cn.tim.router_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 此注解用于Class/interface
@Target(ElementType.TYPE)

// 注解保留到Class
@Retention(RetentionPolicy.SOURCE)
public @interface Router {
    String value();
}