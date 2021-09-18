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

    private String scopeType;
    private String dataScope;
    private String userAlias;
    private String orgAlias;
    private List<String> orgIds;

}
