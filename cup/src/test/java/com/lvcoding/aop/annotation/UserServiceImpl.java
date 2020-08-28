package com.lvcoding.aop.annotation;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author wuyanshen
 * @discription 描述
 * @date 2020-07-07 2:37 下午
 */
public class UserServiceImpl implements UserService, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void login(String username, String password) {
        System.out.println("UserServiceImpl.login");
    }

    @Override
    public void register(User myUser) {
        System.out.println("UserServiceImpl.register");
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.login("小小","1234");
    }
}
