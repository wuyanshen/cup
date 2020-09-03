package com.lvcoding.cup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvcoding.entity.SysTenant;
import com.lvcoding.service.SysTenantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @Description 描述
 * @Date 2020-08-28 12:11 下午
 * @Author wuyanshen
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class SysTenantTest {

    @Autowired
    private SysTenantService sysTenantService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    // 分页查询租户
    @Test
    public void find() throws Exception {
        mockMvc.perform(get("/tenant/page")
                .param("tenantName", "公司")
                .header("tenantId",1))
                .andDo(print());
    }

    // 新增租户
    @Test
    public void add() throws Exception {
        String json = "{\"tenantName\":\"测试公司\",\"remark\":\"测试公司\"}";

        //SysTenant sysTenant = new SysTenant();
        //sysTenant.setTenantName("北京卡普公司");
        //sysTenant.setRemark("一家科技公司");
        //String req = objectMapper.writeValueAsString(sysTenant);

        mockMvc.perform(post("/tenant")
               .contentType(MediaType.APPLICATION_JSON_UTF8)
               .content(json))
               .andDo(print());
    }

    // 更新租户
    @Test
    public void update() throws Exception {
        String json = "{id:1, tenantName:太原卡普公司}";
        SysTenant sysTenant = new SysTenant();
        sysTenant.setId(1);
        sysTenant.setTenantName("太原卡普公司");
        json = objectMapper.writeValueAsString(sysTenant);

        mockMvc.perform(put("/tenant")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andDo(print());
    }

    // 删除租户
    @Test
    public void remove() throws Exception {
        mockMvc.perform(delete("/tenant/2"))
                .andDo(print());
    }

}
