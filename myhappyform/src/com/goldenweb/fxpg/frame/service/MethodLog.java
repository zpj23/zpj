package com.goldenweb.fxpg.frame.service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Lee 
 * @date 2014-2-12 下午5:20:49
 */
@Target({ElementType.METHOD,ElementType.TYPE})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented 
public @interface MethodLog {
	String remark() default "未填写环绕信息";
}
