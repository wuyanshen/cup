package com.lvcoding.exception;

import com.lvcoding.util.Res;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wuyanshen
 * @date 2020-03-21 7:03 下午
 * @discription 全局异常处理
 */
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public Res errorHandler(Exception ex) {
        //判断异常的类型,返回不一样的返回值
        if (ex instanceof ExpiredJwtException) {
            return Res.fail(401, "token过期了,请重新登录");
        }
        else if (ex instanceof AccessDeniedException) {
            return Res.fail(403, "您无权访问该资源");
        }
        else if (ex instanceof MissingServletRequestParameterException) {
            return Res.fail("请求参数错误(参数类型或参数个数不正确)");
        } else {
            return Res.fail(500, "系统异常，请联系管理员");
        }
    }
}
