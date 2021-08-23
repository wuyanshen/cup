package com.lvcoding.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wuyanshen
 * @description 描述
 * @date 2020-07-07 11:14 上午
 */
public class TestJDKProxy {
    public static void main(String[] args) {
        //1.创建原始对象 父类引用指向子类的对象 java多态的体现
        UserService userService = new UserServiceImpl();
        //2.额外功能
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("aop: 方法执行前 ~~~");
                Object obj = method.invoke(userService,args);
                System.out.println("aop: 方法执行后 ~~~");
                return obj;
            }
        };
        //3.创建jdk动态代理
        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(UserServiceImpl.class.getClassLoader(),userService.getClass().getInterfaces(),invocationHandler);
        //3.使用代理类调用方法
        userServiceProxy.login("小花","123");
        userServiceProxy.register(new User());
    }
}
