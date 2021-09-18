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
