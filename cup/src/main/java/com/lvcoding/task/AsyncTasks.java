package com.lvcoding.task;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class AsyncTasks {


    @Async
    @SneakyThrows
    public CompletableFuture<String> doTaskOne(String taskNo) {

        log.info("开始任务: {}", taskNo);
        long start = System.currentTimeMillis();
        Thread.sleep(3000);
        long end = System.currentTimeMillis();

        log.info("完成任务: {}, 耗时: {} 毫秒", taskNo, end-start);
        return CompletableFuture.completedFuture("任务完成");
    }
}
