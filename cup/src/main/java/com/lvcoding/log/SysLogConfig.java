package com.lvcoding.log;

import com.lvcoding.service.SysLogService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author wuyanshen
 * @date 2020-04-23 11:58 下午
 * @discription 日志配置类
 */
@EnableAsync
@AllArgsConstructor
@Configuration
@ConditionalOnWebApplication
public class SysLogConfig {

    private final SysLogService sysLogService;

    @Bean
    public SysLogListener sysLogListener() {
        return new SysLogListener(sysLogService);
    }

    @Bean
    public SysLogAspect sysLogAspect() {
        return new SysLogAspect();
    }
}
