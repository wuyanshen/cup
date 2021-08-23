/*
 *
 *
 *        Copyright (c) 2018-2021, wuyanshen All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *    Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *    Neither the name of the lvcoding.com developer nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *    Author: wuyanshen
 *
 *
 */

package com.lvcoding.util;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @author wuyanshen
 * @date 2020-04-23 10:17 下午
 * @description Spring上下文持有类
 */
@Component
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void cleanHolder() {
        applicationContext = null;
    }

    /**
     * 通过bean字符串获取容器中的bean（默认bean名字的首字母小写，例如：SysUser对应的就是sysUser）
     *
     * @param beanName
     * @return T
     */
    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    /*
     * 通过bean类型获取容器中的bean
     *
     * @param className
     * @return T
     */
    public static <T> T getBean(Class<T> className) {
        return (T) applicationContext.getBean(className);
    }

    /**
     * 发布事件
     *
     * @param event
     * @return void
     */
    public static void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }

    /**
     * 当contex关闭时清理ApplicationContextHolder中存的静态上下文applicationContext
     *
     * @param
     * @return void
     */
    @SneakyThrows
    @Override
    public void destroy() {
        cleanHolder();
    }
}
