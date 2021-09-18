package com.lvcoding.datascope;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据权限ThreadLocal类
 *
 * @date 2021/9/17 下午6:57
 * @author wuyanshen
 */
@Slf4j
public class DataScopeThreadLocal {

    private static final ThreadLocal<DataScopeMetaData> THREAD_LOCAL_DATA_FILTER = new ThreadLocal<>();

    public static void clear() {
        THREAD_LOCAL_DATA_FILTER.remove();
    }

    public static void set(DataScopeMetaData metaData) {
        THREAD_LOCAL_DATA_FILTER.set(metaData);
    }

    public static DataScopeMetaData get() {
        return THREAD_LOCAL_DATA_FILTER.get();
    }
}
