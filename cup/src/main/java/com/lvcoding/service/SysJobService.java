package com.lvcoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lvcoding.entity.SysJob;
import org.quartz.JobDataMap;

public interface SysJobService extends IService<SysJob>{

    void doJob(JobDataMap jobDataMap);

    void add(SysJob sysJob);

    void delete(SysJob sysJob);
}
