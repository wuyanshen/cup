/*
 *
 *
 *        Copyright (c) 2018-2021, wuyanshen All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *    Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *    Neither the name of the lvcoding.com developer nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *    Author: wuyanshen
 *
 *
 */

package com.lvcoding.common;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lvcoding.security.CommonUser;
import com.lvcoding.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description mybatisPlus自动填充
 * @date 2021/8/23 下午4:36
 * @author wuyanshen
 */
@Slf4j
@Component
public class CommonMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入时的填充策略
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("插入策略");
        CommonUser commonUser = SecurityUtil.getUser();
        if(ObjectUtil.isNotEmpty(commonUser)) {
            this.setFieldValByName("createBy", commonUser.getSysUser().getUsername(), metaObject);
        }
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("delFlag", 0, metaObject);
    }

    /**
     * 更新时的填充策略
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("更新策略");
        CommonUser commonUser = SecurityUtil.getUser();
        if(ObjectUtil.isNotEmpty(commonUser)) {
            this.setFieldValByName("updateBy", commonUser.getSysUser().getUsername(), metaObject);
        }
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
