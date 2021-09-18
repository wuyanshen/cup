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

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限枚举类
 *
 * @author wuyanshen
 */
@Getter
@AllArgsConstructor
public enum DataScopeEnum {

    /**
     * 全部
     */
    ALL("1", "全部"),
    /**
     * 自定义
     */
    CUSTOM("2", "自定义"),
    /**
     * 本部门及子部门
     */
    ORGS("3", "本部门及子部门"),
    /**
     * 本部门
     */
    SELF_ORG("4", "本部门"),
    /**
     * 仅自己
     */
    SELF("5", "仅自己");

    /**
     * 数据权限类型
     */
    String type;
    /**
     * 描述
     */
    String desc;

    /**
     * 根据type返回枚举值
     * @param type
     * @return DataScopeEnum
     */
    public static DataScopeEnum getEumByType(String type){
        for(DataScopeEnum testEnums: DataScopeEnum.values()) {
            if(testEnums.getType().equals(type)) {
                return testEnums;
            }
        }
        return null;
    }
}
