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

import cn.hutool.core.util.ObjectUtil;
import com.lvcoding.entity.SysMenu;
import com.lvcoding.entity.dto.TreeNode;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuyanshen
 * @date 2020-03-29 5:40 下午
 * @description 递归建树工具类
 */
@UtilityClass
public class TreeUtil {

    /**
     * 使用两次循环建树
     *
     * @param treeNodes
     * @param root
     * @return java.util.List<T>
     */
    public <T extends TreeNode> List<T> bulidTree(List<T> treeNodes, Object root) {

        List<T> trees = new ArrayList<T>();

        for (T treeNode : treeNodes) {

            //循环父节点
            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }

            //循环第二层子节点
            for (T it : treeNodes) {
                if (it.getParentId() == treeNode.getId()) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.getChildren().add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 使用递归方法建树
     *
     * @param treeNodes
     * @return
     */
    public <T extends TreeNode> List<T> buildByRecursive(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<T>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId() == it.getParentId()) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

    /**
     * 构建树(支持动态根节点)
     *
     * @param treeNodes
     * @param root
     * @return List<SysMenu>
     */
    public List<SysMenu> buildTreeDynamicRoot(List<SysMenu> treeNodes, Integer root) {

        // 动态获取列表中最小的根节点
        SysMenu menu = treeNodes.stream().min(Comparator.comparing(SysMenu::getPid)).get();

        if (ObjectUtil.isNotEmpty(menu)) {
            root = menu.getPid();
        }

        Integer rootVal = root;

        List<SysMenu> menus = new ArrayList<>();

        treeNodes.forEach(parent -> {

            List<SysMenu> children = treeNodes.stream().filter(child -> child.getPid().equals(parent.getId())).collect(Collectors.toList());

            if (ObjectUtil.isNotEmpty(children)) {
                parent.setChildren(children);
            }

            if (parent.getPid().equals(rootVal)) {
                menus.add(parent);
            }

        });

        return menus;
    }
}
