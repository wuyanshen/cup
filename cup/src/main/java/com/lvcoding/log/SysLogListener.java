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

package com.lvcoding.log;

import com.lvcoding.entity.SysLog;
import com.lvcoding.service.SysLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

/**
 * @author wuyanshen
 * @date 2020-04-23 11:51 下午
 * @description 系统日志事件监听器（只要上下文发送事件，就会被监听到）
 */
@Slf4j
@AllArgsConstructor
public class SysLogListener {

    private final SysLogService sysLogService;

    /**
     * 监听到系统日志事件就执行保存
     *
     * @param sysLogEvent
     * @return void
     */
    @Async
    @EventListener(SysLogEvent.class)
    public CompletableFuture<String> eventListen(SysLogEvent sysLogEvent) {
        log.info("开始日志异步任务");

        SysLog sysLog = (SysLog) sysLogEvent.getSource();
        sysLogService.saveLog(sysLog);

        log.info("日志异步任务完成");
        return CompletableFuture.completedFuture("任务完成");
    }
}
