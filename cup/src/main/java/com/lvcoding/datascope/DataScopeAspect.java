package com.lvcoding.datascope;

import com.lvcoding.entity.SysOrg;
import com.lvcoding.entity.SysRole;
import com.lvcoding.security.CommonUser;
import com.lvcoding.service.SysOrgService;
import com.lvcoding.util.SecurityUtil;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuyanshen
 * @description 数据权限切面
 * @date 2021-09-17 上午11:46
 */
@Component
@Aspect
public class DataScopeAspect {

    @Autowired
    private SysOrgService sysOrgService;


    @SneakyThrows
    @Before("@annotation(DataScope)")
    public void around(JoinPoint joinPoint) {

        DataScope dataScope = getDataScope(joinPoint);

        if (dataScope != null) {

            DataScopeMetaData dataScopeMetaData = new DataScopeMetaData();

            // 获取用户角色的数据权限类型
            CommonUser user = SecurityUtil.getUser();
            List<SysRole> sysRoles = user.getRoles();
            SysRole role = sysRoles.stream().min(Comparator.comparing(sysRole -> Integer.parseInt(sysRole.getScopeType().trim()))).get();
            String type = role.getScopeType();
            String scope = role.getDataScope();
            List<SysOrg> childOrgs = sysOrgService.findChildOrgs(user.getSysUser().getOrgId());
            List<String> childOrgIds = childOrgs.stream().map(SysOrg::getId).map(String::valueOf).collect(Collectors.toList());

            // 将数据权限数据放到ThreadLocal中
            dataScopeMetaData.setScopeType(type);
            dataScopeMetaData.setDataScope(scope);
            dataScopeMetaData.setUserAlias(dataScope.userAlias());
            dataScopeMetaData.setOrgAlias(dataScope.orgAlias());
            dataScopeMetaData.setOrgIds(childOrgIds);
            DataScopeThreadLocal.set(dataScopeMetaData);
        }

    }

    /**
     * 获取方法上的数据权限注解
     *
     * @param joinPoint
     * @return DataScope
     */
    private DataScope getDataScope(JoinPoint joinPoint) {
        // 获取注解方法信息
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        DataScope dataScope = null;
        if (method != null) {
            dataScope = method.getAnnotation(DataScope.class);
        }
        return dataScope;
    }
}
