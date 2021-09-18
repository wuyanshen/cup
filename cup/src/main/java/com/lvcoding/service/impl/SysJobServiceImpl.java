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

package com.lvcoding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvcoding.constant.CommonConstant;
import com.lvcoding.dao.SysJobMapper;
import com.lvcoding.entity.SysJob;
import com.lvcoding.job.SpringBeanJob;
import com.lvcoding.service.SysJobService;
import org.quartz.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Scheduler scheduler;

    @Override
    public void doJob(JobDataMap jobDataMap) throws Exception{
        // 将任务对象从dataMap里取出
        SysJob sysJob = (SysJob) jobDataMap.get(CommonConstant.JOB_DATA_KEY);

        String beanName = sysJob.getBeanName();
        String methodName = sysJob.getMethodName();
        Object object = applicationContext.getBean(beanName);

        Method method = object.getClass().getDeclaredMethod(methodName);
        method.invoke(object);
    }

    @Override
    public void add(SysJob sysJob) throws Exception {

        // 构建任务调度器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(sysJob.getCron())
                .withMisfireHandlingInstructionDoNothing();

        // 构建job详细信息
        JobDetail jobDetail = JobBuilder.newJob(SpringBeanJob.class)
                .storeDurably()
                .withIdentity(sysJob.getJobName())
                .withDescription(sysJob.getDescription())
                .build();

        // 将任务对象放在dataMap里
        jobDetail.getJobDataMap().put(CommonConstant.JOB_DATA_KEY, sysJob);

        // 构建trigger触发器
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(sysJob.getJobName())
                .withSchedule(cronScheduleBuilder)
                .build();
            String name = sysJob.getJobName();
            JobKey jobKey = JobKey.jobKey(name);
            boolean exists = scheduler.checkExists(jobKey);
            // 如果任务已经存在，就重置定时任务
            if (exists) {
                scheduler.rescheduleJob(new TriggerKey(name), cronTrigger);
                scheduler.addJob(jobDetail, true);
            } else {
                // 交由scheduler安排触发
                scheduler.scheduleJob(jobDetail, cronTrigger);
            }

            // 更新sys_job表
            SysJob job = this.baseMapper.selectOne(new QueryWrapper<SysJob>().eq("job_name", name));
            if (job != null) {
                this.baseMapper.updateById(sysJob);
            } else {
                this.baseMapper.insert(sysJob);
            }

    }

    @Override
    public void delete(SysJob sysJob) throws Exception {
        if (true) {
            throw new RuntimeException("我自己抛出的异常");
        }

        TriggerKey triggerKey = TriggerKey.triggerKey(sysJob.getJobName());

        // 停止触发器
        scheduler.pauseTrigger(triggerKey);
        // 移除触发器
        scheduler.unscheduleJob(triggerKey);
        // 删除任务
        scheduler.deleteJob(JobKey.jobKey(sysJob.getJobName()));

        // 删除sys_job
        this.baseMapper.deleteById(sysJob.getId());
    }
}
