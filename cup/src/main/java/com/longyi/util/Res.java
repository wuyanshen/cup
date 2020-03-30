package com.longyi.util;

import com.baomidou.mybatisplus.extension.api.R;
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
public class Res<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    public static <T> Res<T> success(Integer code, String msg, T data) {
        return new Res(code, msg, data);
    }

    public static <T> Res<T> success(Integer code, String msg) {
        return new Res(code, msg, "");
    }

    public static <T> Res<T> success(Integer code, T data) {
        return new Res(code, "请求成功", data);
    }

    public static <T> Res<T> success(Integer code) {
        return new Res(code, "请求成功", "");
    }

    public static <T> Res<T> success(String msg) {
        return new Res(0, msg, "");
    }

    public static <T> Res<T> success(T data) {
        return new Res(0, "请求成功", data);
    }

    public static <T> Res<T> success(String msg,T data) {
        return new Res(0, msg, data);
    }

    public static <T> Res<T> success() {
        return new Res(0, "请求成功", "");
    }

    public static <T> Res<T> fail(Integer code, String msg, T data) {
        return new Res(code, msg, data);
    }

    public static <T> Res<T> fail(String msg, T data) {
        return new Res(-1, msg, data);
    }

    public static <T> Res<T> fail(Integer code, String msg) {
        return new Res(code, msg, "");
    }

    public static <T> Res<T> fail(Integer code, T data) {
        return new Res(code, "请求失败", data);
    }

    public static <T> Res<T> fail(Integer code) {
        return new Res(code, "请求失败", "");
    }

    public static <T> Res<T> fail(String msg) {
        return new Res(-1, msg, "");
    }

    public static <T> Res<T> fail() {
        return new Res(-1, "请求失败", "");
    }
}
