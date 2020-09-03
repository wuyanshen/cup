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
