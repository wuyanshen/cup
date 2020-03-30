package com.cup.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuyanshen
 * @date 2020-03-23 1:13 下午
 * @discription 自定义强制下线后的提示
 */
@Component
public class CommonExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletResponse response = event.getResponse();
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        Map<String, Object> map = new HashMap<>(8);
        map.put("msg", "已经在另一台机器登录，您被迫下线。");
        map.put("code", 0);
        String res = objectMapper.writeValueAsString(map);
        writer.write(res);
    }
}
