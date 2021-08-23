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

package com.lvcoding.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvcoding.entity.dto.MenuTree;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;


/**
 * @author wuyanshen
 * @date 2020-03-26 8:47 下午
 * @description 描述
 */
public class Test {


    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public static void main(String[] args) throws JsonProcessingException {
        testJasypt();
    }

    public static void testJasypt(){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("mQjEGflOtK9YuwOS");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("root");
        System.out.println("username:"+username);
        System.out.println("password:"+password);
    }

    public static void testPwd(){
        String pwdStr = "$2a$10$ssErbjkQIqjIR6cbD3JcNOKbJnBbmw/E04sUST7DE2UcLy/rIUJgK";

        System.out.println(passwordEncoder.matches("123456",pwdStr));
    }

    public static void tree() throws JsonProcessingException {

//        //根节点
//        TreeNode root = new TreeNode();
//        root.setId(1);
//        root.setParentId(0);
//
//        //子节点1
//        TreeNode treeNode1 = new TreeNode();
//        treeNode1.setId(2);
//        treeNode1.setParentId(1);
//        treeNode1.setChildren(null);
//
//        //子节点2
//        TreeNode treeNode2 = new TreeNode();
//        treeNode2.setId(3);
//        treeNode2.setParentId(1);
//        treeNode2.setChildren(null);
//
//        root.add(treeNode1);
//        root.add(treeNode2);

//        ObjectMapper objectMapper = new ObjectMapper();
//        String tree = objectMapper.writeValueAsString(root);
//
//        System.out.println(tree);

        MenuTree menuTree1 = new MenuTree();
        menuTree1.setId(1);
        menuTree1.setParentId(0);
        menuTree1.setMenuName("系统管理");

        //二级菜单
        MenuTree menuTree2 = new MenuTree();
        menuTree2.setId(2);
        menuTree2.setParentId(1);
        menuTree2.setMenuName("用户管理");


        MenuTree menuTree3 = new MenuTree();
        menuTree3.setId(3);
        menuTree3.setParentId(1);
        menuTree3.setMenuName("菜单管理");

        MenuTree menuTree4 = new MenuTree();
        menuTree4.setId(4);
        menuTree4.setParentId(1);
        menuTree4.setMenuName("角色管理");

        //三级菜单
        MenuTree menuTree5 = new MenuTree();
        menuTree5.setId(5);
        menuTree5.setParentId(4);
        menuTree5.setMenuName("角色列表");

        //四级菜单
        MenuTree menuTree6 = new MenuTree();
        menuTree6.setId(6);
        menuTree6.setParentId(5);
        menuTree6.setMenuName("角色修改");

        //五级菜单
        MenuTree menuTree7 = new MenuTree();
        menuTree7.setId(7);
        menuTree7.setParentId(6);
        menuTree7.setMenuName("角色修改子级");

        List<MenuTree> list = new ArrayList<MenuTree>();
        list.add(menuTree1);
        list.add(menuTree2);
        list.add(menuTree3);
        list.add(menuTree4);
        list.add(menuTree5);//三级菜单
//        list.add(menuTree6);//四级菜单
//        list.add(menuTree7);//五级菜单

        ObjectMapper objectMapper = new ObjectMapper();
//        List<MenuTree> treeNodes = TreeUtil.buildByRecursive(list,0);
        List<MenuTree> treeNodes = TreeUtil.bulidTree(list,0);
        String trees = objectMapper.writeValueAsString(treeNodes);
        System.out.println(trees);
    }
}
