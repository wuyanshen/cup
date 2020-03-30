package com.cup.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author wuyanshen
 * @date 2020-03-19 1:12 上午
 * @discription 描述
 */
@Aspect
@Component
public class CommonAspect {

    @Pointcut("execution(* com.cup.service.*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before() {
        System.out.println("===before===");
    }
}
