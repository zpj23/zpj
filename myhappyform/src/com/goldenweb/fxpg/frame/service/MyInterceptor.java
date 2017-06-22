/**  
  * @Title: MyInterceptor.java
  * @Package com.goldenweb.core.frame.service
  * @Description: TODO(用一句话描述该文件做什么)
  * @author Lee 
  * @date 2014-2-12 下午3:24:12
  * @version V1.0  
  */ 
package com.goldenweb.fxpg.frame.service;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Lee 
 * @date 2014-2-12 下午3:24:12
 */
@Aspect
public class MyInterceptor {
	
	private Logger logger = Logger.getLogger(MyInterceptor.class);
	
	@Pointcut("execution(* com.goldenweb.core.*.service.*.*(..))")
	
	private void anyMethod(){}//声明一个切入点  
	
	@Before("anyMethod()")//声明前置通知  
	public void doBefore(JoinPoint jionpoint){  
		// 获取被调用的类名  
        String targetClassName = jionpoint.getTarget().getClass().getName();  
        // 获取被调用的方法名  
        String targetMethodName = jionpoint.getSignature().getName();  
        // 日志格式字符串  
        String logInfoText = "前置通知：" + targetClassName + "类的"  
                + targetMethodName + "方法开始执行";  
        // 将日志信息写入配置的文件中  
        logger.info(logInfoText);   
	}
	
	@AfterReturning(pointcut="anyMethod()",returning="result")//声明后置通知  
	public void doAfterReturning(JoinPoint jionpoint){  
		// 获取被调用的类名  
        String targetClassName = jionpoint.getTarget().getClass().getName();  
        // 获取被调用的方法名  
        String targetMethodName = jionpoint.getSignature().getName();  
        // 日志格式字符串  
        String logInfoText = "后置通知：" + targetClassName + "类的"  
                + targetMethodName + "方法开始执行";  
        // 将日志信息写入配置的文件中  
        logger.info(logInfoText); 
	}
	
	@AfterThrowing(pointcut="anyMethod()",throwing="e")//声明例外通知  
	public void doAfterThrowing(JoinPoint jionpoint){  
		 // 获取被调用的类名  
        String targetClassName = jionpoint.getTarget().getClass().getName();  
        // 获取被调用的方法名  
        String targetMethodName = jionpoint.getSignature().getName();  
        // 日志格式字符串  
        String logInfoText = "异常通知：执行" + targetClassName + "类的"  
                + targetMethodName + "方法时发生异常";  
        // 将日志信息写入配置的文件中  
        logger.info(logInfoText);  
	}
	
	@Around("anyMethod()")//声明环绕通知  
	public Object doAround(ProceedingJoinPoint jionpoint)throws Throwable{  
		long beginTime = System.currentTimeMillis();  
        long endTime = System.currentTimeMillis();  
        // 获取被调用的方法名  
        String targetMethodName = jionpoint.getSignature().getName();  
        // 日志格式字符串  
        String logInfoText = "环绕通知：" + targetMethodName + "方法调用前时间" + beginTime  
                + "毫秒," + "调用后时间" + endTime + "毫秒";  
        String methodRemark = this.getMthodRemark(jionpoint); // 
        // 将日志信息写入配置的文件中  
        logger.info(logInfoText);
        logger.info(methodRemark);
        return jionpoint.proceed();
	}
	  
    /**
     * @Title getMthodRemark
     * @Description TODO(获取方法的中文备注用于记录用户的操作日志描述)
     * @param joinPoint
     * @return String
     * @throws Exception String
     * @author Lee
     * @time 2014-2-12 下午4:51:16
     */
    public String getMthodRemark(ProceedingJoinPoint joinPoint)  
            throws Exception {  
        String targetName = joinPoint.getTarget().getClass().getName();  
        String methodName = joinPoint.getSignature().getName();  
        Object[] arguments = joinPoint.getArgs();  
  
        Class targetClass = Class.forName(targetName);  
        Method[] method = targetClass.getMethods();  
        String methode = "";  
        for (Method m : method) {  
            if (m.getName().equals(methodName)) {  
                Class[] tmpCs = m.getParameterTypes();  
                if (tmpCs.length == arguments.length) {  
                    MethodLog methodCache = m.getAnnotation(MethodLog.class); 
                    if(methodCache!=null && !"".equals(methodCache)){
                    	 methode = methodCache.remark();
                    }else{
                    	methode = "未填写环绕信息";
                    }
                    break;  
                }  
            }  
        }  
        return methode;  
    }  
}
