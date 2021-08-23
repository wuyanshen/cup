package com.lvcoding.aop.beanPost;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wuyanshen
 * @description 描述
 * @date 2020-07-07 1:22 下午
 */
public class MyBeanPostProcesser implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("aop: 我在方法前执行了 ~~~");
                Object obj = method.invoke(bean,args);
                System.out.println("aop: 我在方法后执行了 ~~~");
                return obj;
            }
        };
        return Proxy.newProxyInstance(MyBeanPostProcesser.class.getClassLoader(),bean.getClass().getInterfaces(),invocationHandler);
    }
}
