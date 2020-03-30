package com.longyi.util;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wuyanshen
 * @date 2020-03-25 4:32 下午
 * @discription 日期工具类
 */
@UtilityClass
public class DateUtil {

    /**
     * 返回当前日期的字符串
     *
     * @return String
     */
    public String nowString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

}
