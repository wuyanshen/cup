package com.lvcoding.tenant;

import cn.hutool.core.util.StrUtil;
import com.lvcoding.constant.CommonConstant;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 多租户过滤器
 * @Date 2020-08-28 3:00 下午
 * @Author wuyanshen
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tenantId = request.getHeader(CommonConstant.TENANT_HEADER);
        if(StrUtil.isNotEmpty(tenantId)){
            TenantContextHolder.setTenantId(Integer.parseInt(tenantId));
        }
        filterChain.doFilter(request, response);

        // 最后清除ThreadLocal中的值
        TenantContextHolder.clear();
    }
}
