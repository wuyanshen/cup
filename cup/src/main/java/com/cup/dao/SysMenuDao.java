package com.cup.dao;

import com.cup.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单表(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-24 01:24:05
 */
public interface SysMenuDao {

    /**
     * 通过角色名称list查询菜单权限
     *
     * @param permissions
     * @return java.util.List<java.lang.String>
     */
    List<SysMenu> loadPermissionByRoleCode(@Param("permissions") List<String> permissions);


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysMenu queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysMenu> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysMenu 实例对象
     * @return 对象列表
     */
    List<SysMenu> queryAll(SysMenu sysMenu);

    /**
     * 新增数据
     *
     * @param sysMenu 实例对象
     * @return 影响行数
     */
    int insert(SysMenu sysMenu);

    /**
     * 修改数据
     *
     * @param sysMenu 实例对象
     * @return 影响行数
     */
    int update(SysMenu sysMenu);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 根据角色id获取用户菜单
     *
     * @param id
     * @return List<SysMenu>
     */
    List<SysMenu> findMenuByRoleId(Integer id);
}
