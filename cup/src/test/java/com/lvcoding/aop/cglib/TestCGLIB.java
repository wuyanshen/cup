package com.lvcoding.aop.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author wuyanshen
 * @discription 描述
 * @date 2020-07-07 12:22 下午
 */
public class TestCGLIB {

    public static void main(String[] args) {
        //1.创建原始对象
        UserService userService = new UserServiceImpl();
        //2.额外功能
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(UserService.class.getClassLoader());
        enhancer.setSuperclass(UserService.class);
        MethodInterceptor methodInterceptor = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("aop: 方法执行前 ~~");
                Object obj = method.invoke(userService,objects);
                System.out.println("aop: 方法执行后 ~~");
                return obj;
            }
        };
        enhancer.setCallback(methodInterceptor);
        //3.cglib创建动态代理对象
        UserService userServiceProxy = (UserService) enhancer.create();
        userServiceProxy.login("小米","123");
        userServiceProxy.register(new User());
    }
}
