package com.lvcoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lvcoding.entity.SysJob;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;

public interface SysJobService extends IService<SysJob>{

    void doJob(JobDataMap jobDataMap) throws Exception;

    void add(SysJob sysJob) throws Exception;

    void delete(SysJob sysJob) throws Exception;
}
