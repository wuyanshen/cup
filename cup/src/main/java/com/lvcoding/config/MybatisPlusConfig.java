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

package com.lvcoding.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.lvcoding.common.CommonMetaObjectHandler;
import com.lvcoding.datascope.CupDataPermissionHandler;
import com.lvcoding.tenant.CupTenantHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author wuyanshen
 * @date 2020-03-25 4:48 下午
 * @description Mybatis配置
 */

@Configuration
@MapperScan("com.lvcoding.dao")
@EnableTransactionManagement
public class MybatisPlusConfig  {

    /**
     * mybatis拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 多租户拦截器
        // TenantLineInnerInterceptor tenantLineInnerInterceptor = new TenantLineInnerInterceptor();
        // tenantLineInnerInterceptor.setTenantLineHandler(this.tenantLineHandler());
        // interceptor.addInnerInterceptor(tenantLineInnerInterceptor);

        // 数据权限拦截器
        DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor();
        dataPermissionInterceptor.setDataPermissionHandler(this.dataPermissionHandler());
        interceptor.addInnerInterceptor(dataPermissionInterceptor);

        // 向Mybatis过滤器链中添加分页拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 防止全表更新与删除插件: BlockAttackInnerInterceptor
        // interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        // 还可以添加其它的拦截器
        return interceptor;
    }

    /**
     * 注入多租户处理器
     */
    @Bean
    @ConditionalOnMissingBean
    public TenantLineHandler tenantLineHandler() {
        return new CupTenantHandler();
    }

    /**
     * 注入权限范围处理器
     */
    @Bean
    @ConditionalOnMissingBean
    public DataPermissionHandler dataPermissionHandler() {
        return new CupDataPermissionHandler();
    }


    /**
     * 自动填充数据
     */
    @Bean
    @ConditionalOnMissingBean
    public CommonMetaObjectHandler mateMetaObjectHandler() {
        return new CommonMetaObjectHandler();
    }

}
