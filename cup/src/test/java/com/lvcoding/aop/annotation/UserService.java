package com.lvcoding.aop.annotation;

/**
 * @author wuyanshen
 * @discription 描述
 * @date 2020-07-07 2:31 下午
 */
public interface UserService {
    void login(String username, String password);
    void register(User myUser);
}
