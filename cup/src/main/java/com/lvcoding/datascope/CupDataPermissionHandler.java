package com.lvcoding.datascope;

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

            // 数据权限类型
            String type = dataScopeMetaData.getScopeType();
            // 数据权限范围(仅自定义时有值，逗号隔开的部门id)
            String scope = dataScopeMetaData.getDataScope();
            // 用户表别名
            String userAlias = dataScopeMetaData.getUserAlias();
            // 部门表别名
            String orgAlias = dataScopeMetaData.getOrgAlias();
            List<String> orgIds = dataScopeMetaData.getOrgIds();

            switch (type) {
                // 查看全部 where 1=1
                case CommonConstant.DATA_SCOPE_ALL:
                    return where;

                // 自定义
                // where org_id in (orgIds)
                case CommonConstant.DATA_SCOPE_CUSTOMIZE:
                    // 创建IN 表达式
                    // 创建IN范围的元素集合
                    // 把集合转变为JSQLParser需要的元素列表
                    ItemsList itemsList = new ExpressionList(Arrays.stream(scope.split(",")).map(LongValue::new).collect(Collectors.toList()));
                    InExpression inExpression = new InExpression(new Column(String.format("%s.org_id", userAlias)), itemsList);
                    return new AndExpression(where, inExpression);

                // 本部门及子部门
                // where org_id in (userOrgId, childOrgIds)
                case CommonConstant.DATA_SCOPE_DEPARTMENT:
                    // 创建IN 表达式
                    // 创建IN范围的元素集合
                    // 把集合转变为JSQLParser需要的元素列表
                    orgIds.add(String.valueOf(user.getSysUser().getOrgId()));
                    ItemsList departmentList = new ExpressionList(orgIds.stream().map(LongValue::new).collect(Collectors.toList()));
                    InExpression depExpression = new InExpression(new Column(String.format("%s.org_id", userAlias)), departmentList);
                    return new AndExpression(where, depExpression);

                // 本部门
                // where org_id = userOrgId
                case CommonConstant.DATA_SCOPE_SELF_DEPARTMENT:
                    Integer orgId = user.getSysUser().getOrgId();
                    EqualsTo selfDepartmentEqualsTo = new EqualsTo();
                    selfDepartmentEqualsTo.setLeftExpression(new Column(String.format("%s.org_id", userAlias)));
                    selfDepartmentEqualsTo.setRightExpression(new LongValue(orgId));

                    return new AndExpression(where, selfDepartmentEqualsTo);

                // 仅自己
                // where create_by = userId and org_id = userDeptId
                default:
                    Integer id = user.getSysUser().getId();
                    String username = user.getUsername();

                    EqualsTo selfEqualsTo = new EqualsTo();
                    selfEqualsTo.setLeftExpression(new Column(String.format("%s.id", userAlias)));
                    selfEqualsTo.setRightExpression(new LongValue(id));
                    AndExpression andExpression = new AndExpression(where, selfEqualsTo);

                    EqualsTo selfEqualsTo2 = new EqualsTo();
                    selfEqualsTo2.setLeftExpression(new Column(String.format("%s.create_by", userAlias)));
                    selfEqualsTo2.setRightExpression(new StringValue(username));
                    OrExpression orExpression = new OrExpression(andExpression, selfEqualsTo2);
                    AndExpression andExpression2 = new AndExpression(where, selfEqualsTo2);

                    return andExpression2;
            }
        } catch (Exception e) {
            log.error("CupDataPermissionHandler.err", e);
        } finally {
            DataScopeThreadLocal.clear();
        }
        return where;
    }
}
