package com.lvcoding.datascope;

import java.lang.annotation.*;

/**
 * 数据权限注解
 * @author wuyanshen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 用户表的别名
     */
    String userAlias() default "u";

    /**
     * 部门表的别名
     */
    String orgAlias() default "o";

}
