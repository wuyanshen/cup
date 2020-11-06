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
