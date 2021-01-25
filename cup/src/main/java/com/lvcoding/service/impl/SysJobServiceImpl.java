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
    public void doJob(JobDataMap jobDataMap) {
        // 将任务对象从dataMap里取出
        SysJob sysJob = (SysJob) jobDataMap.get(CommonConstant.JOB_DATA_KEY);

        String beanName = sysJob.getBeanName();
        String methodName = sysJob.getMethodName();
        Object object = applicationContext.getBean(beanName);

        try {
            Method method = object.getClass().getDeclaredMethod(methodName);
            method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(SysJob sysJob) {

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

        try {
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

        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(SysJob sysJob) {
        TriggerKey triggerKey = TriggerKey.triggerKey(sysJob.getJobName());

        try {
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(sysJob.getJobName()));

            // 删除sys_job
            this.baseMapper.deleteById(sysJob.getId());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
