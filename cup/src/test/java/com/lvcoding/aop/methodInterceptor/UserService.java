package com.lvcoding.aop.methodInterceptor;

public interface UserService {

    void login(String username,String password);

    void register(User user);
}
