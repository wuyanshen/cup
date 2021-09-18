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

package com.lvcoding.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvcoding.entity.SysTenant;
import com.lvcoding.service.SysTenantService;
import com.lvcoding.util.Res;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 租户控制器
 * @Date 2020-08-28 11:39 上午
 * @Author wuyanshen
 */
@RestController
@RequestMapping("tenant")
@AllArgsConstructor
public class SysTenantController {

    private final SysTenantService sysTenantService;

    /**
     * 新增租户
     *
     * @param sysTenant
     * @return com.lvcoding.util.Res
     */
    @PostMapping
    public Res add(@RequestBody SysTenant sysTenant){
        return Res.success(sysTenantService.save(sysTenant));
    }

    /**
     * 删除租户
     *
     * @param id
     * @return com.lvcoding.util.Res
     */
    @DeleteMapping("{id}")
    public Res delete(@PathVariable("id")Integer id){
        return Res.success(sysTenantService.removeById(id));
    }

    /**
     * 更新租户
     *
     * @param sysTenant
     * @return com.lvcoding.util.Res
     */
    @PutMapping
    public Res update(@RequestBody SysTenant sysTenant){
        return Res.success(sysTenantService.updateById(sysTenant));
    }

    /**
     * 分页查询租户
     *
     * @param page
     * @param sysTenant
     * @return com.lvcoding.util.Res
     */
    @GetMapping("page")
    public Res page(Page page, SysTenant sysTenant){
        QueryWrapper<SysTenant> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isNotEmpty(sysTenant.getTenantName())){
            queryWrapper.lambda().like(SysTenant::getTenantName, sysTenant.getTenantName());
        }
        return Res.success(sysTenantService.page(page, queryWrapper));
    }

    /**
     * 获取租户列表
     *
     * @param
     * @return com.lvcoding.util.Res
     */
    @GetMapping("list")
    public Res list(){
        return Res.success(sysTenantService.list());
    }
}
