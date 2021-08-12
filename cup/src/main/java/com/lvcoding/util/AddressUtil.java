package com.lvcoding.util;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.lvcoding.constant.CommonConstant;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 根据ip获取省，市信息
 * @Author wuyanshen
 */
@UtilityClass
public class AddressUtil {

    private static String URL = "http://whois.pconline.com.cn/ipJson.jsp";

    /**
     * 根据访问的ip获取地址信息
     *
     * @param ip
     */
    public String getAddressByIp(String ip) {
        if ("0:0:0:0:0:0:0:1".equals(ip) ||  NetUtil.isInnerIP(ip)) {
            return "内网IP";
        }

        try {
            // 组装http请求参数
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("ip", ip);
            paramMap.put("json", true);
            // 发送http请求
            String result = HttpUtil.get(URL, paramMap);
            // 格式化返回结果
            Object obj = JSON.parse(result);
            Map<String, String> map = (Map) obj;
            String pro = map.get("pro");
            String city = map.get("city");
            String res = StrUtil.format("{} {}", pro, city);
            return res;
        } catch (Exception e) {
            return CommonConstant.UNKNOWN_IP;
        }

    }

}

