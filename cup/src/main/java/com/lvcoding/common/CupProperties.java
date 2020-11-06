package com.lvcoding.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 描述
 * @Date 2020-11-05 6:59 下午
 * @Author wuyanshen
 */
@Data
@Component
@ConfigurationProperties(prefix = "cup")
public class CupProperties {

    /**
     * 是否开启演示模式
     */
    private boolean enableDemo = false;

    /**
     * 不需要校验token的url
     */
    private List<String> tokenIgnoreUrl = new ArrayList<>();
}