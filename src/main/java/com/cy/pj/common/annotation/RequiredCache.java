package com.cy.pj.common.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解：(使用@interface定义注解，默认所有注解都是一个Annotation类型的对象)
 * @Target 注解用于告诉jdk我们自己写的注解可以描述的对象。
 * @Retention 注解用于告诉JDK我们自己写的注解何时有效。
 * 说明：所有的注解都是一种元素据(Meta Data)-一种描述数据的数据(例如使用注解描述类，
 * 描述方法，描述属性，描述方法参数等等)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiredCache {

}
