package com.lvcoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lvcoding.entity.SysRole;
import com.lvcoding.entity.dto.SysRoleDTO;

import java.util.List;

/**
 * 角色表(SysRole)表服务接口
 *
 * @author makejava
 * @since 2020-03-24 01:24:31
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysRole queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysRole> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysRole 实例对象
     * @return 实例对象
     */
    SysRole insert(SysRole sysRole);

    /**
     * 修改数据
     *
     * @param sysRole 实例对象
     * @return 实例对象
     */
    SysRole update(SysRole sysRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 删除角色
     *
     * @param id
     * @return boolean
     */
    boolean delete(Integer id);

    /**
     * 通过角色id查询菜单id集合
     *
     * @param id
     * @return java.util.List<java.lang.Integer>
     */
    List<Integer> findMenuIds(Integer id);

    boolean saveRoleMenu(SysRoleDTO sysRoleDTO);
}
