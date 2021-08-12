package com.lvcoding;

import cn.hutool.core.lang.UUID;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvcoding.entity.SysUser;
import com.lvcoding.service.SysUserService;
import com.lvcoding.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author wuyanshen
 */
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 将用户存转为json存到redis中
     */
    @Test
    public void addUser() throws JsonProcessingException {
        SysUser sysUser = sysUserService.getById(1);
//        String user = objectMapper.writeValueAsString(sysUser);
//        redisTemplate.opsForValue().set("cup:user", user);
        RedisUtil.set("cup:user", sysUser, 30, TimeUnit.SECONDS);
    }

    /**
     * 获取redis中的用户信息
     */
    @Test
    public void getUser() throws IOException {
//        String result = redisTemplate.opsForValue().get("cup:user").toString();
        SysUser sysUser = RedisUtil.get("cup:user");
//        SysUser sysUser = null;
//        if (obj instanceof JSONObject){
//            sysUser = (SysUser)obj;
//        }
//        SysUser sysUser = objectMapper.readValue(objectMapper.writeValueAsString(obj), SysUser.class);
        System.out.println(sysUser);
    }

    @Test
    public void deleUser() {
        boolean flag = RedisUtil.del("cup:user");
        if (flag) {
            System.out.println("删除用户成功");
        }
    }


    /**
     * 将uuid存到redis中
     */
    @Test
    public void StringTest() {
        String uuid = UUID.randomUUID().toString();
//        redisTemplate.opsForValue().set("cup:String:test", uuid);
        RedisUtil.set("cup:String:test", uuid);
    }

    @Test
    public void RedisKeys() {
//        Set<String> stringSet = redisTemplate.keys("cup*");
        Set<String> list = RedisUtil.list("cup*");
        list.stream().forEach(System.out::println);
    }

    public static void main(String[] args) {
        String uuid = UUID.randomUUID(true).toString();
        System.out.println(uuid);
    }
}
