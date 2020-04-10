package com.lvcoding.service;

import com.lvcoding.entity.SysOrg;

import java.util.List;

/**
 * 组织机构表(SysOrg)表服务接口
 *
 * @author makejava
 * @since 2020-03-24 01:24:17
 */
public interface SysOrgService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysOrg queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysOrg> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysOrg 实例对象
     * @return 实例对象
     */
    SysOrg insert(SysOrg sysOrg);

    /**
     * 修改数据
     *
     * @param sysOrg 实例对象
     * @return 实例对象
     */
    SysOrg update(SysOrg sysOrg);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
