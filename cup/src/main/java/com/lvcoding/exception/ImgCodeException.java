package com.lvcoding.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author wuyanshen
 * @discription 图片验证码异常类
 */
public class ImgCodeException extends AuthenticationException {

    public ImgCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ImgCodeException(String msg) {
        super(msg);
    }
}
