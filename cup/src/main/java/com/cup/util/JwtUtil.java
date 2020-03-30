package com.cup.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wuyanshen
 * @date 2020-03-25 2:31 下午
 * @discription Jwt加密，验证工具类
 */
@UtilityClass
public class JwtUtil {

    /**
     * key（按照签名算法的字节长度设置key）
     */
    private final static String SECRET_KEY = "0123456789_0123456789_0123456789";

    /**
     * 过期时间（毫秒单位）
     * 默认设置1小时
     */
    private final static long TOKEN_EXPIRE_MILLIS = 1000 * 60 * 60;


    /**
     * 生成token
     *
     * @param authentication
     * @return java.lang.String
     */
    public String createToken(Authentication authentication) {
        Map<String, Object> tokenMap = new HashMap(2);
        tokenMap.put("username", authentication.getName());
        tokenMap.put("created", new Date());
        return generateToken(tokenMap);
    }

    /**
     * 生成token
     *
     * @param claimMap
     * @return java.lang.String
     */
    public String generateToken(Map<String, Object> claimMap) {
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                // 设置签发时间
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_MILLIS))
                //设置主体内容
                .addClaims(claimMap)
                //设置签名算法
                .signWith(generateKey())
                .compact();
    }

    /**
     * 校验token
     *
     * @param token
     * @return java.lang.Boolean
     */
    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token);
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
    public Map<String, Object> parseToken(String token) {
        // 得到DefaultJwtParser
        return Jwts.parser()
                // 设置签名密钥
                .setSigningKey(generateKey())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 刷新token(通过旧token换取新的token)
     *
     * @param
     * @return String
     */
    public String refreshToken(String token){
        Map<String,Object> claims = parseToken(token);
        Map<String,Object> newClaims = new HashMap<>();
        newClaims.put("username",claims.get("username"));
        newClaims.put("created", new Date());

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                // 设置签发时间
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_MILLIS))
                //设置主体内容
                .addClaims(newClaims)
                //设置签名算法
                .signWith(generateKey())
                .compact();
    }

    /**
     * 生成安全密钥
     *
     * @return
     */
    public Key generateKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

}
