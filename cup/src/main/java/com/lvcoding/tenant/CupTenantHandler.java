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

package com.lvcoding.tenant;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.lvcoding.constant.CommonConstant;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.stereotype.Component;

/**
 * @Description cup多租户处理类
 * @Date 2020-08-28 10:20 上午
 * @Author wuyanshen
 */
@Component
@AllArgsConstructor
public class CupTenantHandler implements TenantHandler {

    private final TenantProperties tenantProperties;

    @Override
    public Expression getTenantId(boolean where) {
        // 从租户上下文获取租户id
        Integer tenantId = TenantContextHolder.getTenantId();
        if(ObjectUtil.isNotEmpty(tenantId)){
            return new LongValue(tenantId);
        }

        // 如果租户上下文不存在租户id，默认返回1
        return new LongValue(1);
    }

    @Override
    public String getTenantIdColumn() {
        // 租户字段名称
        return CommonConstant.TENANT_ID;
    }

    @Override
    public boolean doTableFilter(String tableName) {

        // 判断是否开启多租户功能
        if(!tenantProperties.isEnable()){
            return true;
        }

        // 判断租户id是否为null，null的话就是租户系统管理员，不进行租户过滤
        Integer tenantId = TenantContextHolder.getTenantId();
        if(tenantId == null){
            return true;
        }

        // 不过滤租户表
        if(tableName.equalsIgnoreCase(CommonConstant.TENANT_TABLE)){
            return true;
        }

        // 判断不需要过滤的表
        if(tenantProperties.getIgnoreTables().contains(tableName)){
            return true;
        }

        return false;
    }
}
