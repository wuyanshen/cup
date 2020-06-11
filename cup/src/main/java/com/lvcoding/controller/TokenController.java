package com.lvcoding.controller;

import com.lvcoding.util.JwtUtil;
import com.lvcoding.util.Res;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyanshen
 * @date 2020-03-25 5:18 下午
 * @discription token相关控制器
 */
@RequestMapping("token")
@RestController
public class TokenController {

    @GetMapping("check")
    public Res checkToken(@RequestHeader("token") String token) {
        if (StringUtils.isEmpty(token)) {
            return Res.fail("token不能为空");
        }
        if (JwtUtil.validateToken(token)) {
            return Res.success("token有效", true);
        }
        return Res.fail("token无效", false);
    }

    @GetMapping("refresh")
    public Res refreshToken(@RequestHeader("token") String token) {
        if (StringUtils.isEmpty(token)) {
            return Res.fail("token不能为空");
        }
        return Res.success("刷新token成功", JwtUtil.refreshToken(token));
    }
}
