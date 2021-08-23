/*
 *
 *
 *        Copyright (c) 2018-2021, wuyanshen All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *    Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *    Neither the name of the lvcoding.com developer nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *    Author: wuyanshen
 *
 *
 */

package com.lvcoding.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author wuyanshen
 * @description 图片验证码异常类
 */
public class ImgCodeException extends AuthenticationException {

    public ImgCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ImgCodeException(String msg) {
        super(msg);
    }
}