package com.lvcoding.log;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @date 2020/4/24 9:51 上午
 * @author wuyanshen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     * 日志标题
     *
     * @author wuyanshen
     * @return {String}
     */
    String value();

    /**
     * 日志类型(1:用户日志,2:菜单日志,3:角色日志)
     *
     * @author wuyanshen
     * @return {String}
     */
    String type() default "0";
}
