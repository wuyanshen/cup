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

import java.util.List;

/**
 * @description 数据权限处理器
 * @date   2021-08-24 下午6:35
 * @author  wuyanshen
 */
public interface DataScopeHandler {

    /**
     * 是否需要进行数据权限过滤
     *
     * @param depIds
     * @return boolean
     */
    boolean getDataScope(List<String> depIds);
}
