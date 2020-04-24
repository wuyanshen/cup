package com.lvcoding.log;

import com.lvcoding.entity.SysLog;
import com.lvcoding.service.SysLogService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * @author wuyanshen
 * @date 2020-04-23 11:51 下午
 * @discription 系统日志事件监听器（只要上下文发送事件，就会被监听到）
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
