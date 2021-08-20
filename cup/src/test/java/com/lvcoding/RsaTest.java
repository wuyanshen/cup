package com.lvcoding;

import com.lvcoding.util.RsaUtil;

import org.junit.Test;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

public class RsaTest {

    @Test
    public void rsaTests() {
        Console.log("aaa");
    }

    public static void main(String[] args) throws Exception {

        // String pubKey =
        // "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiOn6nGu8Y2+hjro9eK/7JKoUh8JUTPwgT9nALay1wBM7fvOKCZy6wrcgFsOvmyC176e+jLJYTDODErgO3V9V2M+n0QAeo5kDb4oWJhhcWLiAVWdfdisnXJ51bKoKWp/bb4yDX6IHXVZe6rCDxopflwS8G1FKsukEfBBbP8PjfDQIDAQAB";
        // String priKey =
        // "MIICXAIBAAKBgQCiOn6nGu8Y2+hjro9eK/7JKoUh8JUTPwgT9nALay1wBM7fvOKCZy6wrcgFsOvmyC176e+jLJYTDODErgO3V9V2M+n0QAeo5kDb4oWJhhcWLiAVWdfdisnXJ51bKoKWp/bb4yDX6IHXVZe6rCDxopflwS8G1FKsukEfBBbP8PjfDQIDAQABAoGAM+YOuprtPsCrWahwiSn9+pE1wiPbLSsPBIPkWGLTSHDhVcZxmFI2J6OQx/FPpqcHlgrC9SrAYBvQlFsvKSpaSVHPzeIhZzJ1Ka7alFCj9MceJGFxt4RGfzcE5HsOMe0Tf4/IlxaPU/akurkxFgtcWvMOKz1wM53bdOtKaApK6IECQQDVPnkLhziPQ1UXwWkStUdz6fZ0dIbLvxHhzeey0z6ZrrVKllwetSZoZQvVGD1ZynEI0+dCnY4bFzPIPpDma8r9AkEAwsF2o6Xod0UTrHUDt6JPJEIkRgsMC0hdhBTPtFARUYr7Wx8e1HEbnrn672hxH2FqeIHTgQ8+Jdqj9zT6oYHJUQJAdZvEBMCqSBE5sCVivweuBdcGr0nJQjv6L9BxNmZdg0MhB6cP3XvJWBBKy1dYFtqZJuZACLR+uKA+VfVz0zGmPQJAaNCEEEjvFpmXn/4N9RumakYqjYPOhJf6tGYa7tkUqQUaiAz0o7MIAWHoekaEczYTfi2o7dGNSgQksJvTs25NMQJBALmmG5shFjTnyrkBcW27jPZIBkzZgQDlqQ3sce/iSm+rYE8R4Dd5WKdh8hXJRpnJEEn2yq/Nl5Qma+vLU8hsxio=";

        // RSA rsa = new RSA(priKey, null);

        String rsaPassword = "XuFKqsdgt0Eln7ltHfyeHmJwJ45g+HGRQgZQhSu8EOrawn9vsjgWCl5XW0N5iPvBfoV/lsA3la56zzEaQCki4C2hPGMIGifthNsKMiK2n7uLMz4/mcu9ghcg9VgVwD7BZVFRzqx3xGBjYhacczdxSgGxj7ZnteJ26gyAg4H81L8=";

        // String decrypt = rsa.decryptStr(rsaPassword, KeyType.PrivateKey);

        // System.out.println("decrypt: " + decrypt);
        String password = RsaUtil.decode(rsaPassword);

        System.out.println(password);
    }

    /**
     * RSA公钥加密
     * 
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    // public static String encrypt(String str, String publicKey) throws Exception {
    // // base64编码的公钥
    // byte[] decoded = Base64.decodeBase64(publicKey);
    // RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
    // .generatePublic(new X509EncodedKeySpec(decoded));
    // // RSA加密
    // Cipher cipher = Cipher.getInstance("RSA");
    // cipher.init(Cipher.ENCRYPT_MODE, pubKey);
    // String outStr =
    // Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
    // return outStr;
    // }

    /**
     * RSA私钥解密
     * 
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    // public static String decrypt(String str, String privateKey) throws Exception
    // {
    // // 64位解码加密后的字符串
    // byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
    // // base64编码的私钥
    // byte[] decoded = Base64.decodeBase64(privateKey);
    // RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
    // .generatePrivate(new PKCS8EncodedKeySpec(decoded));
    // // RSA解密
    // Cipher cipher = Cipher.getInstance("RSA");
    // cipher.init(Cipher.DECRYPT_MODE, priKey);
    // String outStr = new String(cipher.doFinal(inputByte));
    // return outStr;
    // }
}