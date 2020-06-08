package com.lvcoding.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvcoding.entity.SysLog;
import com.lvcoding.service.SysLogService;
import com.lvcoding.util.Res;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyanshen
 * @date 2020-06-05 4:50 下午
 * @discription 日志控制器
 */
@RestController
@RequestMapping("logs")
@AllArgsConstructor
public class SysLogController {

    private final SysLogService sysLogService;

    /**
     * 分页查询日志
     *
     * @param page
     * @param sysLog
     * @return com.lvcoding.util.Res
     */
    @RequestMapping("page")
    public Res page(Page page, SysLog sysLog){
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(sysLog.getTitle())){
            queryWrapper.lambda().like(SysLog::getTitle,sysLog.getTitle());
        }
        if(!StringUtils.isEmpty(sysLog.getType())){
            queryWrapper.lambda().eq(SysLog::getType,sysLog.getType());
        }
        queryWrapper.lambda().orderByDesc(SysLog::getCreateTime);
        return Res.success(sysLogService.page(page,queryWrapper));
    }
}
