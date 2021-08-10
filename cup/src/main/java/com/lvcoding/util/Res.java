package com.lvcoding.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wuyanshen
 * @date 2020-03-25 3:56 下午
 * @discription 自定义返回信息类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Res implements Serializable {

    private static final long serialVersionUID = -3146576490912309065L;

    private Integer code;
    private String msg;
    private Object data;

    public static  Res success(Integer code, String msg, Object data) {
        return new Res(code, msg, data);
    }

    public static  Res success(Integer code, String msg) {
        return new Res(code, msg, "");
    }

    public static  Res success(Integer code, Object data) {
        return new Res(code, "请求成功", data);
    }

    public static  Res success(Integer code) {
        return new Res(code, "请求成功", "");
    }

    public static  Res success(String msg) {
        return new Res(0, msg, "");
    }

    public static  Res success(Object data) {
        return new Res(0, "请求成功", data);
    }

    public static  Res success(String msg,Object data) {
        return new Res(0, msg, data);
    }

    public static  Res success() {
        return new Res(0, "请求成功", "");
    }

    public static  Res fail(Integer code, String msg, Object data) {
        return new Res(code, msg, data);
    }

    public static  Res fail(String msg, Object data) {
        return new Res(-1, msg, data);
    }

    public static  Res fail(Integer code, String msg) {
        return new Res(code, msg, "");
    }

    public static  Res fail(Integer code, Object data) {
        return new Res(code, "请求失败", data);
    }

    public static  Res fail(Integer code) {
        return new Res(code, "请求失败", "");
    }

    public static  Res fail(String msg) {
        return new Res(-1, msg, "");
    }

    public static  Res fail() {
        return new Res(-1, "请求失败", "");
    }
}
