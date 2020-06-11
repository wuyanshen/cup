package com.lvcoding.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lvcoding.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单表(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-24 01:24:05
 */
public interface SysMenuDao extends BaseMapper<SysMenu> {

    /**
     * 通过角色名称list查询菜单权限
     *
     * @param permissions
     * @return java.util.List<java.lang.String>
     */
    List<SysMenu> loadPermissionByRoleCode(@Param("permissions") List<String> permissions);

    /**
     * 通过菜单id删除角色菜单关联表
     *
     * @param menuId
     * @return int
     */
    int deleteMenuRoleById(Integer menuId);

    /**
     * 根据角色id集合获取用户菜单
     *
     * @param roleIds
     * @return List<SysMenu>
     */
    List<SysMenu> findMenuByRoleIds(@Param("roleIds") List<Integer> roleIds);
}
