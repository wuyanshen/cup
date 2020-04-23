package com.lvcoding.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lvcoding.entity.SysUser;
import com.lvcoding.entity.vo.SysUserVO;

import java.util.List;

/**
 * 用户表(SysUser)表服务接口
 *
 * @author makejava
 * @since 2020-03-24 01:44:06
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysUser queryById(Integer id);


    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    SysUser insert(SysUser sysUser);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    SysUser update(SysUser sysUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);


    /**
     * 查询所有用户
     *
     * @param sysUser
     * @return Res
     */
    List<SysUser> findAll(SysUser sysUser);

    /**
     * 分页查询用户信息
     *
     * @param page
     * @param sysUser
     * @return com.longyi.util.Res
     */
    IPage<SysUser> selectUserPage(Page<SysUser> page, SysUser sysUser);

    boolean updatePwd(SysUserVO sysUser);
}
