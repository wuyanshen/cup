package com.lvcoding.aop.beanPost;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wuyanshen
 * @discription 描述
 * @date 2020-07-07 10:42 上午
 */
public class BeanPostTest {

    @Test
    public void test2(){
        ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-beanpost.xml");
        UserService userService = (UserService) context.getBean("userService");
        userService.login("小明","123");
        userService.register(new User());
    }
}
