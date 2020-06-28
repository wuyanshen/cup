package com.lvcoding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvcoding.entity.SysMenu;
import com.lvcoding.dao.SysMenuDao;
import com.lvcoding.entity.dto.MenuTree;
import com.lvcoding.service.SysMenuService;
import com.lvcoding.util.TreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单表(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2020-03-24 01:24:05
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        boolean flag1 = this.baseMapper.deleteMenuRoleById(id) > 0?true:false;
        boolean flag2 = this.baseMapper.deleteById(id) > 0?true:false;
        return flag1&&flag2;
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
        List<MenuTree> menuTrees = menuList.stream().map(menu -> {
            MenuTree menuTree = new MenuTree();
            BeanUtils.copyProperties(menu, menuTree);
            menuTree.setId(menu.getId());
            menuTree.setParentId(menu.getMenuPid());
            return menuTree;
        }).collect(Collectors.toList());
        List<MenuTree> trees = TreeUtil.buildByRecursive(menuTrees, 0);
        return trees;
    }


    @Override
    public boolean addMenu(MenuTree menuTree) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(menuTree,sysMenu);
        sysMenu.setMenuPid(menuTree.getParentId());
        //新增菜单默认有效
        sysMenu.setStatus(1);
        //设置父id集合
        this.setMenuPids(sysMenu);
        return this.baseMapper.insert(sysMenu)>0;
    }

    private void setMenuPids(SysMenu child){
        List<SysMenu> sysMenus = this.baseMapper.selectList(new QueryWrapper<>());
        for (SysMenu sysMenu:sysMenus){
            if (sysMenu.getId().equals(child.getMenuPid())){
                if(ObjectUtils.isEmpty(sysMenu.getMenuPids()) || sysMenu.getMenuPids().equals("0")){
                    child.setMenuPids(sysMenu.getId()+"");
                }else{
                    child.setMenuPids(sysMenu.getMenuPids()+","+child.getMenuPid());
                }
            }
        }
    }

    /**
     * 查询所有的菜单树
     *
     * @param
     * @return java.util.List<com.lvcoding.entity.dto.MenuTree>
     */
    @Override
    public List<MenuTree> findAllMenuTree() {
        List<SysMenu> sysMenus = this.baseMapper.selectList(new QueryWrapper<>());
        List<MenuTree> collect = sysMenus.stream().map(sysMenu -> {
            MenuTree menuTree = new MenuTree();
            BeanUtils.copyProperties(sysMenu, menuTree);
            menuTree.setParentId(sysMenu.getMenuPid());
            menuTree.setId(sysMenu.getId());
            return menuTree;
        }).collect(Collectors.toList());
        List<MenuTree> menuTrees = TreeUtil.buildByRecursive(collect, 0);
        return menuTrees;
    }
}
