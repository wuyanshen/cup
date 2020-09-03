package com.lvcoding.tenant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 多租户属性配置类
 * @Date 2020-08-28 10:21 上午
 * @Author wuyanshen
 */
@Data
@Component
@ConfigurationProperties(prefix = "cup.tenant")
public class TenantProperites {

    /**
     *  是否开启租户功能，默认不开启（false）
     */
    private boolean enable = false;

    /**
     * 不需要过滤的表，默认为空
     */
    private List<String> ignoreTables = new ArrayList<>();

}
