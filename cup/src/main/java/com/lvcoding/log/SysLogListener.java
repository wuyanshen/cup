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

package com.lvcoding.log;

import com.lvcoding.entity.SysLog;
import com.lvcoding.service.SysLogService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * @author wuyanshen
 * @date 2020-04-23 11:51 下午
 * @description 系统日志事件监听器（只要上下文发送事件，就会被监听到）
 */
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
    public void eventListen(SysLogEvent sysLogEvent) {
        SysLog sysLog = (SysLog) sysLogEvent.getSource();
        sysLogService.save(sysLog);
    }
}
