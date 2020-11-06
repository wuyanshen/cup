package com.lvcoding.security;

import cn.hutool.core.util.StrUtil;
import com.lvcoding.constant.CommonConstant;
import com.lvcoding.redis.RedisService;
import com.lvcoding.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author wuyanshen
 * @date 2020-07-01 3:48 下午
 * @discription token处理类
 */
@Component
public class TokenService {

    /**
     * 分转毫秒
     */
    private static final long MINUTE_TO_MILLISECOND = 60 * 1000;

    private static final long REFRESH_MILLISECOND = 20 * 60 * 1000;


    @Autowired
    private RedisService redisService;

    /**
     * token请求头
     */
    @Value("${token.header}")
    private String header;

    /**
     * token秘钥
     */
    @Value("${token.secret}")
    private String secret;

    /**
     * token有效期(默认30分钟)
     */
    @Value("${token.expireTime}")
    private int expireTime;


    /**
     * 获取用户身份信息
     *
     * @param request
     */
    public CommonUser getCommonUser(HttpServletRequest request) {
        String token = getToken(request);
        if(StrUtil.isNotEmpty(token)){
            Map<String,Object> map = JwtUtil.parseToken(token, secret);
            String uuid = map.get(CommonConstant.TOKEN_KEY).toString();
            String loginTokenKey = getKey(uuid);
            CommonUser commonUser = redisService.get(loginTokenKey);
            return commonUser;
        }
        return null;
    }

    private String getKey(String uuid) {
        return CommonConstant.TOKEN_REDIS_KEY + uuid;
    }

    /**
     * 获取token
     *
     * @param request
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(this.header);
        if(StrUtil.isNotEmpty(token) && token.startsWith(CommonConstant.TOKEN_PREFIX)){
            token = token.replaceAll(CommonConstant.TOKEN_PREFIX, "");
        }
        return token;
    }


    /**
     * 校验token
     *
     * @param commonUser
     */
    public void validateToken(CommonUser commonUser){
        long time = System.currentTimeMillis() - commonUser.getLoginTime();
        if(time <= REFRESH_MILLISECOND){
            refreshToken(commonUser);
        }
    }

    /**
     * 生成token
     *
     * @param commonUser
     */
    public String createToken(CommonUser commonUser) {
        Map<String, Object> tokenMap = new HashMap(2);
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        tokenMap.put(CommonConstant.TOKEN_KEY, uuid);
        String token = JwtUtil.createToken(tokenMap, secret);

        commonUser.setToken(uuid);
        refreshToken(commonUser);
        return token;
    }

    /**
     * 从redis删除token
     *
     */
    public Boolean deleteToken(String token){
        if(StrUtil.isNotEmpty(token)){
            return redisService.del(CommonConstant.TOKEN_REDIS_KEY+token);
        }
        return false;
    }

    /**
     * 刷新token
     *
     * @param commonUser
     */
    private void refreshToken(CommonUser commonUser) {
        commonUser.setLoginTime(System.currentTimeMillis());
        commonUser.setExpireTime(System.currentTimeMillis() + expireTime * MINUTE_TO_MILLISECOND);

        String key = getKey(commonUser.getToken());
        //用redis缓存token
        redisService.set(key, commonUser, expireTime, TimeUnit.MINUTES);
    }


}
