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

package com.lvcoding.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvcoding.entity.SysJob;
import com.lvcoding.service.SysJobService;
import com.lvcoding.util.Res;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuyanshen
 * @date 2020-06-05 4:50 下午
 * @description 定时任务控制器
 */
@Slf4j
@RestController
@RequestMapping("jobs")
@AllArgsConstructor
public class SysJobController {

    private final SysJobService sysJobService;

    private final Scheduler scheduler;

    /**
     * 分页查询任务
     *
     * @param page
     * @param sysLog
     * @return com.lvcoding.util.Res
     */
     @PreAuthorize("@pm.hasPermission('sys:job:view')")
    @RequestMapping("page")
    public Res page(Page page, SysJob sysJob) throws Exception{
        QueryWrapper<SysJob> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(sysJob.getJobName())){
            queryWrapper.lambda().like(SysJob::getJobName,sysJob.getJobName());
        }
        queryWrapper.lambda().orderByDesc(SysJob::getCreateTime);
        return Res.success(sysJobService.page(page,queryWrapper));
    }

    /**
     * 新增/修改任务
     *
     * @param sysJob
     * @return com.lvcoding.util.Res
     */
    @PreAuthorize("@pm.hasPermission('sys:job:add')")
    @PostMapping
    public Res saveOrUpdate(@RequestBody SysJob sysJob) throws Exception{
        String msg = "";
        if (ObjectUtils.isEmpty(sysJob.getId())) {
            log.info(String.format("新增任务 【 %s 】", sysJob.getJobName()));
            msg = "新增任务成功";
        } else {
            log.info(String.format("修改任务 【 %s 】", sysJob.getJobName()));
            msg = "修改任务成功";
        }
        sysJobService.add(sysJob);

        return Res.success(msg);
    }


    /**
     * 删除任务
     *
     * @param sysJob
     * @return com.lvcoding.util.Res
     */
    @PreAuthorize("@pm.hasPermission('sys:job:delete')")
    @DeleteMapping
    public Res delete(@RequestBody SysJob sysJob) throws Exception {
        log.info(String.format("删除任务 【 %s 】", sysJob.getJobName()));
        sysJobService.delete(sysJob);
        return Res.success("删除任务成功");
    }

    /**
     * 手动执行一次任务
     *
     * @param sysJob
     * @return com.lvcoding.util.Res
     */
    @PreAuthorize("@pm.hasPermission('sys:job:trigger')")
    @PutMapping("trigger")
    public Res triggerJob(@RequestBody SysJob sysJob){
        log.info(String.format("手动执行任务 【 %s 】", sysJob.getJobName()));
        JobKey jobKey = JobKey.jobKey(sysJob.getJobName());
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return Res.success("手动执行任务成功");
    }

    /**
     * 暂停任务
     *
     * @param sysJob
     * @return com.lvcoding.util.Res
     */
    @PreAuthorize("@pm.hasPermission('sys:job:pause')")
    @PutMapping("pause")
    public Res pause(@RequestBody SysJob sysJob){
        log.info(String.format("暂停任务 【 %s 】", sysJob.getJobName()));
        JobKey jobKey = JobKey.jobKey(sysJob.getJobName());
        try {
            sysJobService.updateById(sysJob);
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return Res.success("暂停任务成功");
    }

    /**
     * 恢复任务
     *
     * @param sysJob
     * @return com.lvcoding.util.Res
     */
    @PreAuthorize("@pm.hasPermission('sys:job:resume')")
    @PutMapping("resume")
    public Res resume(@RequestBody SysJob sysJob){
        log.info(String.format("恢复任务 【 %s 】", sysJob.getJobName()));
        JobKey jobKey = JobKey.jobKey(sysJob.getJobName());
        try {
            sysJobService.updateById(sysJob);
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return Res.success("恢复任务成功");
    }

    /**
     * 检查cron表达式是否正确
     *
     * @param sysJob
     * @return com.lvcoding.util.Res
     */
    @GetMapping("check")
    public Res check(SysJob sysJob) {
        boolean res = CronExpression.isValidExpression(sysJob.getCron());
        return Res.success(0, "", res);
    }
}
