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
    String userAlias() default "";

    /**
     * 部门表的别名
     */
    String orgAlias() default "";

    /**
     * 用户表字段
     */
    String userColumn() default "id";

    /**
     * 部门表字段
     */
    String orgColumn() default "org_id";

}
