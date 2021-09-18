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

package com.lvcoding.job;

import com.lvcoding.service.SysJobService;
import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description 这里不需要使用@Component，
 * 因为SpringBeanJobFactory类会将SpringBeanJob任务类注入到IOC容器中
 * https://www.cnblogs.com/jason1990/archive/2019/06/30/11110196.html
 *
 * @Date 2021-01-19 18:19 下午
 * @Author wuyanshen
 */
public class SpringBeanJob implements Job {

    @Autowired
    private SysJobService sysJobService;

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) {
        sysJobService.doJob(context.getJobDetail().getJobDataMap());
    }
}
