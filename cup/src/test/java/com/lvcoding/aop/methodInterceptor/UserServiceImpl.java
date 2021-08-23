package com.lvcoding.aop.methodInterceptor;


/**
 * @author wuyanshen
 * @description 描述
 * @date 2020-07-07 10:41 上午
 */
public class UserServiceImpl implements UserService {
    @Override
    public void login(String username, String password) {
        System.out.println(username + "用户登录执行了~~~");
    }

    @Override
    public void register(User user) {
        System.out.println("用户注册执行了~~~");
    }
}
