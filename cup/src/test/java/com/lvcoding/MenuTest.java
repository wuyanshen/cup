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

package com.lvcoding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvcoding.entity.SysMenu;
import com.lvcoding.service.SysMenuService;
import com.lvcoding.util.TreeUtil;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @description 描述
 * @date   2021-08-27 下午4:14
 * @author  wuyanshen
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class MenuTest {

    @Autowired
    private SysMenuService sysMenuService;

    @SneakyThrows
    @Test
    public void treeTest() {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuName("用户查询");
        List<SysMenu> menuList = sysMenuService.findMenuList(sysMenu);
        List<SysMenu> tree = TreeUtil.buildTreeDynamicRoot(menuList, 0);
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println("tree = " + objectMapper.writeValueAsString(tree));
    }

}
