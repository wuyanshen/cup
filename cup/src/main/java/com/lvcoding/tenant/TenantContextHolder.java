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

import lombok.experimental.UtilityClass;

/**
 * @Description 租户上下文，维护租户id
 * @Date 2020-08-28 10:42 上午
 * @Author wuyanshen
 */
@UtilityClass
public class TenantContextHolder {

    private final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    /**
     * 获取租户id
     */
    public Integer getTenantId() {
        return threadLocal.get();
    }

    /**
     * 设置租户id
     */
    public void setTenantId(Integer tenantId){
        threadLocal.set(tenantId);
    }

    /**
     * 清空threadLocal
     */
    public void clear(){
        threadLocal.remove();
    }
}
