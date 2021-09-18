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

package com.lvcoding.util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author wuyanshen
 * @description 解析报错信息
 * @date 2021-02-16 上午12:45
 */
@UtilityClass
public class ExceptionUtil {


    public String getMessage(Exception e) {
        // 注意我们可以使用try-with-resources语句，因为StringWriter和PrintWriter都实现了java.io.Closeable。这样，我们无需在代码中显式关闭它们
        try {
            try (StringWriter stringWriter = new StringWriter();
                 PrintWriter printWriter = new PrintWriter(stringWriter)){

                e.printStackTrace(printWriter);
                printWriter.flush();
                stringWriter.flush();
                return stringWriter.toString();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
