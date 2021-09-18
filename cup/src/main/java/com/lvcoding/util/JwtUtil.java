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

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wuyanshen
 * @date 2020-03-25 2:31 下午
 * @description Jwt加密，验证工具类
 */
@UtilityClass
public class JwtUtil {


    /**
     * 生成token
     *
     * @param secret
     * @return java.lang.String
     */
    public String createToken(Map<String, Object> claimMap,String secret) {
        return generateToken(claimMap, secret);
    }

    /**
     * 生成token
     *
     * @param claimMap
     * @return java.lang.String
     */
    public String generateToken(Map<String, Object> claimMap, String secret) {
        return Jwts.builder()
//                .setId(UUID.randomUUID().toString())
                // 设置签发时间
//                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 设置过期时间
//                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                //设置主体内容
                .addClaims(claimMap)
                //设置签名算法
                .signWith(generateKey(secret))
                .compact();
    }

    /**
     * 校验token
     *
     * @param token
     * @return java.lang.Boolean
     */
    public Boolean validateToken(String token, String secret) {
        try {
            Jwts.parser().setSigningKey(generateKey(secret)).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 解析token
     *
     * @param token
     * @return Map<Object>
     */
    public Map<String, Object> parseToken(String token, String secret) {
        // 得到DefaultJwtParser
        return Jwts.parser()
                // 设置签名密钥
                .setSigningKey(generateKey(secret))
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 刷新token(通过旧token换取新的token)
     *
     * @param
     * @return String
     */
    public String refreshToken(String token, String secret){
        Map<String,Object> claims = parseToken(token, secret);
        Map<String,Object> newClaims = new HashMap<>();
        newClaims.put("username",claims.get("username"));
        newClaims.put("created", new Date());

        return Jwts.builder()
//                .setId(UUID.randomUUID().toString())
                // 设置签发时间
//                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 设置过期时间
//                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                //设置主体内容
                .addClaims(newClaims)
                //设置签名算法
                .signWith(generateKey(secret))
                .compact();
    }

    /**
     * 生成安全密钥
     *
     * @return
     */
    public Key generateKey(String secret) {
        return new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

}
