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

package com.lvcoding.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.google.code.kaptcha.Producer;
import com.lvcoding.constant.CommonConstant;
import com.lvcoding.util.RedisUtil;
import com.lvcoding.util.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/captcha")
public class CodeController
{
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    // 验证码类型
    @Value("${cup.captchaType}")
    private String captchaType;


    /**
     * 验证码生成
     */
    @GetMapping(value = "/captchaImage")
    public Res getKaptchaImage(HttpServletResponse response){
        // 保存验证码信息
        String uuid = IdUtil.simpleUUID();
        String verifyKey = CommonConstant.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        // 验证码存到redis，5分钟过期
        RedisUtil.set(verifyKey, code, 5, TimeUnit.MINUTES);

        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return Res.fail(e.getMessage());
        }

        Map<String, String> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("img", Base64.encode(os.toByteArray()));

        return Res.success("请求成功", map);
    }
}
