/*
 *
 *
 *        Copyright (c) 2018-2021, wuyanshen All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *    Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *    Neither the name of the lvcoding.com developer nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *    Author: wuyanshen
 *
 *
 */

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
