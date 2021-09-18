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

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description 多租户处理器
 * @date   2021-08-25 上午11:19
 * @author  wuyanshen
 */
public class CupTenantHandler implements TenantLineHandler {

    @Autowired
    private TenantProperties tenantProperties;

    /**
     * 获取租户ID
     */
    @Override
    public Expression getTenantId() {
        Integer tenant = TenantContextHolder.getTenantId();
        if (tenant != null) {
            return new StringValue(TenantContextHolder.getTenantId() + "");
        }
        return new LongValue(1);
    }

    /**
     * 获取多租户的字段名
     * @return String
     */
    @Override
    public String getTenantIdColumn() {
        return tenantProperties.getColumn();
    }

    /**
     * 过滤不需要根据租户隔离的表
     * 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
     * @param tableName 表名
     */
    @Override
    public boolean ignoreTable(String tableName) {
        return tenantProperties.getIgnoreTables().stream().anyMatch(
                (t) -> t.equalsIgnoreCase(tableName)
        );
    }
}
