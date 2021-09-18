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

package com.lvcoding.constant;

/**
 * @author wuyanshen
 * @date 2020-03-25 10:33 下午
 * @description 常量
 */
public interface CommonConstant {

    String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";

    String CHECK_TOKEN_URI = "/token/check";

    String WEB_SOCKET = "/websocket";

    String FAVICON = "/favicon.ico";

    String REFRESH_TOKEN_URI = "/token/refresh";

    String LOGIN_URI = "/certification";

    String POST = "post";

    String PUT = "put";

    String DELETE = "delete";

    String LOGIN_TYPE_JSON = "JSON";

    String LOGIN_TYPE_HTML = "HTML";

    String TOKEN_PREFIX = "Bearer ";

    String TOKEN_REDIS_KEY = "login_token_key:";

    String CAPTCHA_CODE_KEY = "captcha_code_key:";

    String TOKEN_KEY = "token_key";

    String UNKNOWN_IP = "XX XX";

    /**
     * 租户表名称
     */
    String TENANT_TABLE = "sys_tenant";

    /**
     * 租户id字段名称
     */
    String TENANT_ID = "tenant_id";

    /**
     * 请求头中租户id名称
     */
    String TENANT_HEADER = "tenantId";

    /**
     * 定时任务数据key
     */
    String JOB_DATA_KEY = "JOB_DATA_KEY";


    /**
     * 待审核
     */
    String LEAVE_BILL_STATUS_CREATE = "0";

    /**
     * 驳回
     */
    String LEAVE_BILL_STATUS_REJECT = "-1";

    /**
     * 不通过
     */
    String LEAVE_BILL_STATUS_REFUSE = "-2";

    /**
     * 通过
     */
    String LEAVE_BILL_STATUS_OK = "1";

    /**
     * 流程定义key
     */
    String LEAVE_BILL_KEY = "newleave";

    /**
     * http的get请求
     */
    String HTTP_METHOD_GET = "get";

    /**
     * 数据权限：全部数据
     */
    String DATA_SCOPE_ALL = "1";

    /**
     * 数据权限：自定义
     */
    String DATA_SCOPE_CUSTOMIZE = "2";

    /**
     * 数据权限：本部门及子部门
     */
    String DATA_SCOPE_DEPARTMENT = "3";

    /**
     * 数据权限：本部门
     */
    String DATA_SCOPE_SELF_DEPARTMENT = "4";

    /**
     * 数据权限：仅自己
     */
    String DATA_SCOPE_SELF = "5";
}
