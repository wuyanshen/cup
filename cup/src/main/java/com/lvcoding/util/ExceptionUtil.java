package com.lvcoding.util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author wuyanshen
 * @discription 解析报错信息
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
