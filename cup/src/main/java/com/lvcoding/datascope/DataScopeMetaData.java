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

import lombok.Data;

import java.util.List;

/**
 * @description 数据权限元数据
 * @date   2021-09-17 下午4:37
 * @author  wuyanshen
 */
@Data
public class DataScopeMetaData {

    private DataScopeEnum scopeType;
    private String dataScope;
    private String userAlias;
    private String orgAlias;
    private String orgColumn;
    private String userColumn;
    private List<String> orgIds;

}
