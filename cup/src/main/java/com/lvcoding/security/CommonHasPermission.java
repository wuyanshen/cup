package com.lvcoding.security;

import cn.hutool.core.util.ObjectUtil;
import com.lvcoding.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

/**
 * @author wuyanshen
 * @date 2020-03-24 2:52 下午
 * @discription 自定义动态判断权限
 */
@Component("pm")
public class CommonHasPermission {

    @Autowired
    private TokenService tokenService;


    public boolean hasPermission(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        CommonUser commonUser = tokenService.getCommonUser(RequestUtil.getRequest());
        if (ObjectUtil.isEmpty(commonUser)) {
            return false;
        }

        return commonUser.getPermissions().stream()
                .filter(StringUtils::hasText)
                .anyMatch(x -> PatternMatchUtils.simpleMatch(permission, x));
    }

}
