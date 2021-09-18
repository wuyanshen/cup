/*
 *
 *        Copyright (c) 2021-2015, wuyanshen All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the lvcoding.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: wuyanshen
 *
 */

package com.lvcoding.util;

import lombok.experimental.UtilityClass;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Description Jasypt安全框架工具类
 * @Author wuyanshen
 */
@UtilityClass
public class JasyptUtil {

    /**
     * 加密和解密需要的盐
     */
    @Value("${jasypt.encryptor.password}")
    private String password = "mQjEGflOtK9YuwOS";

    /**
     * 解密方法
     * @param value
     */
    public String decrypt(String value){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(password);
        String res = textEncryptor.decrypt(value);
        return res;
    }

    /**
     * 加密方法
     * @param value
     */
    public String encrypt(String value){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(password);
        String res = textEncryptor.encrypt(value);
        return res;
    }

    public static void main(String[] args) {
        System.out.println(encrypt("root"));
        System.out.println(decrypt("1Lq+ohzqoojbvjGDo20lOw=="));
    }
}
