package com.cup.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cup.entity.SysUser;

import java.util.List;

/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-24 01:44:06
 */
public interface SysUserDao extends BaseMapper<SysUser> {

    /**
     * 通过用户名查询用户
     *
     * @param username
     * @return ysUser
     */
    SysUser loadUserByUsername(String username);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysUser queryById(Integer id);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysUser 实例对象
     * @return 对象列表
     */
    List<SysUser> queryAll(SysUser sysUser);


    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    int update(SysUser sysUser);


    /**
     * 分页查询
     *
     * @param page
     * @param sysUser
     * @return IPage<SysUser>
     */
    IPage<SysUser> selectPageVo(Page<?> page, SysUser sysUser);
}
