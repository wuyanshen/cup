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

package com.lvcoding.datascope;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.lvcoding.constant.CommonConstant;
import com.lvcoding.security.CommonUser;
import com.lvcoding.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuyanshen
 * @description 数据权限处理器
 * @date 2021-09-17 下午3:09
 */

@Slf4j
public class CupDataPermissionHandler implements DataPermissionHandler {

    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {


        // 1. 获取权限过滤相关信息
        DataScopeMetaData dataScopeMetaData = DataScopeThreadLocal.get();

        // 当前登录用户信息
        CommonUser user = SecurityUtil.getUser();

        try {

            if (dataScopeMetaData == null || user == null) {
                return where;
            }

            log.debug("开始进行权限过滤,dataScopeMetaData:{} , where: {},mappedStatementId: {}", dataScopeMetaData, where, mappedStatementId);

            Expression expression = new HexValue(" 1 = 1 ");
            if (where == null) {
                where = expression;
            }

            // 数据权限范围(仅自定义时有值，逗号隔开的部门id)
            String scope = dataScopeMetaData.getDataScope();
            // 用户表别名
            String userAlias = dataScopeMetaData.getUserAlias();
            // 部门表别名
            String orgAlias = dataScopeMetaData.getOrgAlias();
            String orgColumn = dataScopeMetaData.getOrgColumn();
            String userColumn = dataScopeMetaData.getUserColumn();
            List<String> orgIds = dataScopeMetaData.getOrgIds();

            switch (dataScopeMetaData.getScopeType()) {

                // 查看全部 where 1=1
                case ALL:
                    return where;

                // 自定义
                // where org_id in (orgIds)
                case CUSTOM:
                    // 创建IN 表达式
                    // 创建IN范围的元素集合
                    // 把集合转变为JSQLParser需要的元素列表
                    ItemsList itemsList = new ExpressionList(Arrays.stream(scope.split(",")).map(LongValue::new).collect(Collectors.toList()));
                    InExpression inExpression = new InExpression(new Column(this.formatSql(orgAlias, orgColumn)), itemsList);
                    return new AndExpression(where, inExpression);

                // 本部门及子部门
                // where org_id in (userOrgId, childOrgIds)
                case ORGS:
                    // 创建IN 表达式
                    // 创建IN范围的元素集合
                    // 把集合转变为JSQLParser需要的元素列表
                    orgIds.add(String.valueOf(user.getSysUser().getOrgId()));
                    ItemsList departmentList = new ExpressionList(orgIds.stream().map(LongValue::new).collect(Collectors.toList()));
                    InExpression depExpression = new InExpression(new Column(this.formatSql(orgAlias, orgColumn)), departmentList);
                    return new AndExpression(where, depExpression);

                // 本部门
                // where org_id = userOrgId
                case SELF_ORG:
                    Integer orgId = user.getSysUser().getOrgId();
                    EqualsTo selfDepartmentEqualsTo = new EqualsTo();
                    selfDepartmentEqualsTo.setLeftExpression(new Column(this.formatSql(orgAlias, orgColumn)));
                    selfDepartmentEqualsTo.setRightExpression(new LongValue(orgId));
                    return new AndExpression(where, selfDepartmentEqualsTo);

                // 仅自己
                // where create_by = userId and org_id = userDeptId
                default:
                    String username = user.getUsername();
                    EqualsTo selfEqualsTo = new EqualsTo();
                    String sqlFormat;
                    if (StrUtil.isNotBlank(userAlias)) {
                        sqlFormat = String.format("%s.create_by", userAlias);
                    } else {
                        sqlFormat = String.format("%s", "create_by");
                    }
                    selfEqualsTo.setLeftExpression(new Column(sqlFormat));
                    selfEqualsTo.setRightExpression(new StringValue(username));
                    return new AndExpression(where, selfEqualsTo);
            }

        } catch (Exception e) {
            log.error("CupDataPermissionHandler.err", e);
        } finally {
            DataScopeThreadLocal.clear();
        }
        return where;
    }

    public String formatSql(String orgAlias, String orgColumn) {
        String sqlFormat;
        if (StrUtil.isNotBlank(orgAlias)) {
            sqlFormat = String.format("%s.%s", orgAlias, orgColumn);
        } else {
            sqlFormat = String.format("%s", orgColumn);
        }

        return sqlFormat;
    }
}
