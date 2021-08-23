package com.lvcoding.aop.methodInterceptor;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wuyanshen
 * @description 描述
 * @date 2020-07-07 3:11 下午
 */
public class MethodInterceptorTest {

    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-methodInterceptor.xml");
        UserService userService = (UserService) context.getBean("userService");
        userService.login("小红", "123");
        userService.register(new User());
    }
}
