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

package com.lvcoding.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvcoding.activiti.SecurityUtil;
import com.lvcoding.constant.CommonConstant;
import com.lvcoding.entity.SysUser;
import com.lvcoding.service.SysUserService;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvcoding.entity.LeaveBill;
import com.lvcoding.dao.LeaveBillMapper;
import com.lvcoding.service.LeaveBillService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LeaveBillServiceImpl extends ServiceImpl<LeaveBillMapper, LeaveBill> implements LeaveBillService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private LeaveBillService leaveBillService;
    @Autowired
    private ProcessRuntime processRuntime;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addLeaveBill(LeaveBill leaveBill) {
        // ?????????
        leaveBill.setStatus(CommonConstant.LEAVE_BILL_STATUS_CREATE);
        // ????????????
        leaveBillService.save(leaveBill);

        SysUser user = sysUserService.getById(leaveBill.getUserId());

        runtimeService.startProcessInstanceByKey(CommonConstant.LEAVE_BILL_KEY, leaveBill.getId().toString());
//        processRuntime.start(ProcessPayloadBuilder.start().withProcessDefinitionKey(CommonConstant.LEAVE_BILL_KEY).build());

        // ???????????????
        Task task = taskService.createTaskQuery().taskAssignee(user.getUsername().toString())
                .processDefinitionKey(CommonConstant.LEAVE_BILL_KEY)
                .processInstanceBusinessKey(leaveBill.getId().toString())
                .singleResult();
        taskService.complete(task.getId());

    }

    @Override
    public Page findPage(Page page, LeaveBill leaveBill) {
        QueryWrapper<LeaveBill> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isNotEmpty(leaveBill.getReason())){
            queryWrapper.lambda().like(LeaveBill::getReason, leaveBill.getReason());
        }
        if(StrUtil.isNotEmpty(leaveBill.getStatus())){
            queryWrapper.lambda().eq(LeaveBill::getStatus, leaveBill.getStatus());
        }
        if(ObjectUtil.isNotEmpty(leaveBill.getUserId())){
            queryWrapper.lambda().eq(LeaveBill::getUserId, leaveBill.getUserId());
        }
        return this.page(page, queryWrapper);
    }

    @Override
    public boolean deleteLeaveBill(String id) {
        return this.removeById(id);
    }

    @Override
    public boolean updateLeaveBill(LeaveBill leaveBill) {
        return this.updateById(leaveBill);
    }
}

