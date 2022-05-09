/*
 *
 *        Copyright (c) 2021-2015, wuyanshen All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the lvcoding.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: wuyanshen
 *
 */

package com.lvcoding.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvcoding.entity.SysMenu;
import com.lvcoding.dao.SysMenuMapper;
import com.lvcoding.entity.dto.MenuTree;
import com.lvcoding.service.SysMenuService;
import com.lvcoding.util.TreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 菜单表(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2020-03-24 01:24:05
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        boolean flag1 = this.baseMapper.deleteMenuRoleById(id) > 0 ? true : false;
        boolean flag2 = this.baseMapper.deleteById(id) > 0 ? true : false;
        return flag1 && flag2;
    }

    /**
     * 根据角色id获取用户菜单
     *
     * @param roleIds
     * @return List<SysMenu>
     */
    @Override
    public List<MenuTree> findMenuByRoleIds(List<Integer> roleIds) {
        List<SysMenu> menuList = baseMapper.findMenuByRoleIds(roleIds);
        // 由于一个用户有多个角色的情况，菜单会有重复的情况，这里需要去重一下
        menuList = menuList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(SysMenu::getId))), ArrayList::new));
        List<MenuTree> menuTrees = menuList.stream().map(menu -> {
            MenuTree menuTree = new MenuTree();
            BeanUtils.copyProperties(menu, menuTree);
            menuTree.setId(menu.getId());
            menuTree.setParentId(menu.getPid());
            return menuTree;
        }).sorted(Comparator.comparing(MenuTree::getSort)).collect(Collectors.toList());
        List<MenuTree> trees = TreeUtil.buildByRecursive(menuTrees, 0);
        return trees;
    }


    @Override
    public boolean addMenu(SysMenu sysMenu) {
        //新增菜单默认有效
        sysMenu.setStatus(1);
        //设置父id集合
        this.setMenuPids(sysMenu);
        return this.baseMapper.insert(sysMenu) > 0;
    }

    private void setMenuPids(SysMenu child) {
        List<SysMenu> sysMenus = this.baseMapper.selectList(new QueryWrapper<>());
        for (SysMenu sysMenu : sysMenus) {
            if (sysMenu.getId().equals(child.getPid())) {
                if (ObjectUtils.isEmpty(sysMenu.getPids()) || sysMenu.getPids().equals("0")) {
                    child.setPids(sysMenu.getId() + "");
                } else {
                    child.setPids(sysMenu.getPids() + "," + child.getPids());
                }
            }
        }
    }

    /**
     * 查询所有的菜单树
     *
     * @param
     * @param sysMenu
     * @return List<MenuTree>
     */
    @Override
    public List<SysMenu> findAllMenuTree(SysMenu sysMenu) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(sysMenu.getMenuName())) {
            queryWrapper.lambda().like(SysMenu::getMenuName, sysMenu.getMenuName());
        }
        List<SysMenu> sysMenus = this.baseMapper.selectList(queryWrapper);
        List<SysMenu> menuTrees = TreeUtil.buildTreeDynamicRoot(sysMenus, 0);
        return menuTrees;
    }

    @Override
    public List<SysMenu> findMenuList(SysMenu sysMenu) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotBlank(sysMenu.getMenuName())) {
            queryWrapper.lambda().like(SysMenu::getMenuName, sysMenu.getMenuName());
        }

        List<SysMenu> sysMenuList = this.baseMapper.selectList(queryWrapper);
        return sysMenuList;
    }
}
