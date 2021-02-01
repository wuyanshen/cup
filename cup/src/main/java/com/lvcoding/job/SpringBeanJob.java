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
