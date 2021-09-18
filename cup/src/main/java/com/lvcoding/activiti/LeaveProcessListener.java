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

package com.lvcoding.activiti;

import com.lvcoding.entity.SysUser;
import com.lvcoding.service.SysUserService;
import com.lvcoding.util.SecurityUtil;
import com.lvcoding.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wuyanshen
 * @description 请假任务监听器
 * @date 2021-02-03 上午9:09
 */
@Component
@Slf4j
public class LeaveProcessListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        // 当前登录用户
        String username = Objects.requireNonNull(SecurityUtil.getUser()).getUsername();

        SysUserService sysUserService = SpringContextHolder.getBean(SysUserService.class);

        if(delegateTask.getName().contains("提交")) {
            delegateTask.setAssignee(username);
        } else {
            // 获取用户上级
            List<SysUser> userList = sysUserService.findSuperior(username);
            if (userList == null) {
                log.info("用户 {} 没有上级，将由当前用户作为任务候选人", username);
                delegateTask.setAssignee(username);
            } else {
                List<String> users = userList.stream().map(SysUser::getUsername).collect(Collectors.toList());
                log.info("用户 {} 有上级，任务候选人：", String.join(",", users));
                delegateTask.addCandidateUsers(users);
            }
        }

    }
}
