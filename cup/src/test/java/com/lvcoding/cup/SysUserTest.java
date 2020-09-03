package com.lvcoding.cup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


/**
 * @Description 用户测试类
 * @Date 2020-08-28 3:16 下午
 * @Author wuyanshen
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class SysUserTest {

    @Autowired
    private MockMvc mockMvc;

    // 新增用户
    @Test
    public void add() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("")
                .header("tenantId", "1"))
                .andDo(MockMvcResultHandlers.print());
    }

    // 删除用户

    // 更新用户

    // 查询用户
    @Test
    public void find() throws Exception{
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl9rZXkiOiI3NGE4ZjUxMDgwZTg0N2QxOTIxZGZhN2FjM2Y4NDY3OCJ9.n-aqh0k9Ny2xBnnC7Bqb9Mh5EUjwY-_eCJldy-z733c";
        mockMvc.perform(MockMvcRequestBuilders.get("/users/page")
                //.params("")
                .header("tenantId", "1").header("Authorization",token))
                .andDo(MockMvcResultHandlers.print());
    }
}
