package com.lvcoding.constant;

/**
 * @author wuyanshen
 * @date 2020-03-25 10:33 下午
 * @discription 常量
 */
public interface CommonConstant {

    String CHECK_TOKEN_URI = "/token/check";

    String WEB_SOCKET = "/websocket";

    String REFRESH_TOKEN_URI = "/token/refresh";

    String LOGIN_URI = "/certification";

    String POST = "post";

    String LOGIN_TYPE_JSON = "JSON";

    String LOGIN_TYPE_HTML = "HTML";

    String TOKEN_PREFIX = "Bearer ";

    String TOKEN_REDIS_KEY = "login_token_key:";

    String TOKEN_KEY = "token_key";

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

}
