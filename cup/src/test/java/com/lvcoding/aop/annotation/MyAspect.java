package com.lvcoding.aop.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author wuyanshen
 * @discription 描述
 * @date 2020-07-07 2:24 下午
 */
@Aspect
public class MyAspect {

    @Pointcut("execution(* com.lvcoding.aop.annotation.*.*(..))")
    public void pointcut(){
    }

    @Around("pointcut()")
    private Object aspect(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("aop: 方法前执行 ~~~");
        Object obj = joinPoint.proceed();
        System.out.println("aop: 方法后执行 ~~~");
        return obj;
    }
}
