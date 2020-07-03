package com.lvcoding.controller;

import cn.hutool.core.util.ObjectUtil;
import com.lvcoding.security.CommonUser;
import com.lvcoding.security.TokenService;
import com.lvcoding.util.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuyanshen
 * @date 2020-03-25 5:18 下午
 * @discription token相关控制器
 */
@RequestMapping("token")
@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    /**
     * 校验token是否有效
     *
     */
    @GetMapping("/check")
    public Res refreshToken(HttpServletRequest request) {
        CommonUser commonUser = tokenService.getCommonUser(request);
        if (ObjectUtil.isEmpty(commonUser)) {
            return Res.fail(401,"token无效或不存在");
        }
        return Res.success("token有效");
    }
}
