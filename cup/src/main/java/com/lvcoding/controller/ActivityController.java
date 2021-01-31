package com.lvcoding.controller;

import com.lvcoding.activiti.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ActivityController {

    @Autowired
    private ActivityService activityService;


    @GetMapping("act")
    public void act() {
        activityService.startActivity();
    }
}
