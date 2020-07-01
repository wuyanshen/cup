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
}
