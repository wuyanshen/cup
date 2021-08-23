package com.lvcoding.aop.beanPost;

/**
 * @author wuyanshen
 * @description 描述
 * @date 2020-07-07 2:37 下午
 */
public class UserServiceImpl implements UserService {
    @Override
    public void login(String username, String password) {
        System.out.println("UserServiceImpl.login");
    }

    @Override
    public void register(User user) {
        System.out.println("UserServiceImpl.register");
    }
}
