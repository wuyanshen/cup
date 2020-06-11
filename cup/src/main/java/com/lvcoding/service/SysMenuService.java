package com.lvcoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lvcoding.entity.SysMenu;
import com.lvcoding.entity.dto.MenuTree;

import java.util.List;

/**
 * 菜单表(SysMenu)表服务接口
 *
 * @author makejava
 * @since 2020-03-24 01:24:05
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 根据角色id获取用户菜单
     *
     * @param roleIds
     * @return List<SysMenu>
     */
    List<MenuTree> findMenuByRoleIds(List<Integer> roleIds);

    boolean addMenu(MenuTree menuTree);

    List<MenuTree> findAllMenuTree();
}
