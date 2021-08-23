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

package com.lvcoding.datascope;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * 数据权限拦截器
 *
 * @date 2021/8/23 下午7:17
 * @author wuyanshen
 */
public class DataScopeInterceptor implements InnerInterceptor {

	@Override
	public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
							ResultHandler resultHandler, BoundSql boundSql) {
		PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);

		// 原始sql
		String originalSql = boundSql.getSql();

		// sql参数
		Object parameterObject = boundSql.getParameterObject();

		// 先判断是不是SELECT操作 不是直接跳过
		if (!SqlCommandType.SELECT.equals(ms.getSqlCommandType())) {
			return;
		}

		// 优先获取赋值数据
			originalSql = String.format("SELECT %s FROM (%s) temp_data_scope", "*",
					originalSql);
			mpBs.sql(originalSql);


		// if (deptIds.isEmpty()) {
		// 	originalSql = String.format("SELECT %s FROM (%s) temp_data_scope WHERE 1 = 2",
		// 			dataScope.getFunc().getType(), originalSql);
		// }
		// else {
		// 	String join = CollectionUtil.join(deptIds, ",");
		// 	originalSql = String.format("SELECT %s FROM (%s) temp_data_scope WHERE temp_data_scope.%s IN (%s)",
		// 			dataScope.getFunc().getType(), originalSql, scopeName, join);
		// }

	}


}
