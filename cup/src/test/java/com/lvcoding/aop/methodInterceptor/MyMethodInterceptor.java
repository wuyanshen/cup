package com.lvcoding.aop.methodInterceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author wuyanshen
 * @description 描述
 * @date 2020-07-07 10:37 上午
 */

public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        System.out.println("------我在方法前执行了-------");
        Object obj = methodInvocation.proceed();
        System.out.println("------我在方法后执行了-------");

        return obj;
    }
}
