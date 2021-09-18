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

package com.lvcoding.datascope.nouse;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.lvcoding.datascope.DataScope;
import lombok.Setter;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * 数据权限拦截器
 *
 * @author wuyanshen
 * @date 2021/8/23 下午7:17
 */
public class DataScopeInterceptor implements InnerInterceptor {

    @Setter
    private DataScopeHandler dataScopeHandler;

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
                            ResultHandler resultHandler, BoundSql boundSql) {
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);

        // 原始sql
        String originalSql = boundSql.getSql();

        // sql参数
        Object parameterObject = boundSql.getParameterObject();

        // 先判断是不是SELECT操作，不是就直接跳过
        if (!SqlCommandType.SELECT.equals(ms.getSqlCommandType())) {
            return;
        }

        DataScope dataScope = getDataScopeFromParameterObject(parameterObject);

        // 如果不包含DdataScope数据权限对象，表示不进行数据权限过滤，直接跳过
        if (dataScope == null) {
            return;
        }

        List<String> orgIds = null;
        String scopeColumn = null;

        if (dataScopeHandler.getDataScope(orgIds)) {
            return;
        }

        // 格式化原始sql
        originalSql = String.format("SELECT %s FROM (%s) temp_data_scope", "*",
                originalSql);

        if (orgIds.isEmpty()) {
            originalSql = String.format("SELECT %s FROM (%s) temp_data_scope WHERE 1 = 2",
                    "*", originalSql);
        } else {
            // 只查询自己新建的数据
            if(orgIds.contains("SELF")) {
                originalSql = String.format("SELECT %s FROM (%s) temp_data_scope WHERE temp_data_scope.%s = '%s'",
                        "*", originalSql, "create_by", orgIds.get(1));
            } else {
                String join = CollectionUtil.join(orgIds, ",");
                originalSql = String.format("SELECT %s FROM (%s) temp_data_scope WHERE temp_data_scope.%s IN (%s)",
                        "*", originalSql, scopeColumn, join);
            }

        }

        mpBs.sql(originalSql);

    }

    /**
     * 从parameterObject中解析是否有DataScope对象
     *
     * @param parameterObject
     * @return DataScope
     */
    private DataScope getDataScopeFromParameterObject(Object parameterObject) {
        if (parameterObject instanceof Map) {
            for (Object val : ((Map<?, ?>) parameterObject).values()) {
                if (val instanceof DataScope) {
                    return (DataScope) val;
                }
            }
        }
        return null;
    }


}
