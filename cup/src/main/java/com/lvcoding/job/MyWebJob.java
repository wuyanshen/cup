package com.lvcoding.job;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description 描述
 * @Date 2021-01-14 2:54 下午
 * @Author wuyanshen
 */
@Component
public class MyWebJob {

    public void test() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("定时任务触发 " + sdf.format(new Date()));
    }

    public void abc() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("定时任务触发22222 " + sdf.format(new Date()));
    }
}
