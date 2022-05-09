package com.lvcoding.controller;

import com.lvcoding.task.AsyncTasks;
import com.lvcoding.util.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequestMapping("test")
@RestController
public class TestController {

    @Autowired
    private AsyncTasks asyncTasks;

    @GetMapping("task/one")
    public Res taskOne() {
        log.info("开始执行方法了");
        CompletableFuture<String> stringCompletableFuture = asyncTasks.doTaskOne("1");

        return Res.success();
    }
}
