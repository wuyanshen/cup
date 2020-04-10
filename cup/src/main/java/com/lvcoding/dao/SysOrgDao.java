package com.lvcoding.dao;

import com.lvcoding.entity.SysOrg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织机构表(SysOrg)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-24 01:24:17
 */
public interface SysOrgDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysOrg queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysOrg> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysOrg 实例对象
     * @return 对象列表
     */
    List<SysOrg> queryAll(SysOrg sysOrg);

    /**
     * 新增数据
     *
     * @param sysOrg 实例对象
     * @return 影响行数
     */
    int insert(SysOrg sysOrg);

    /**
     * 修改数据
     *
     * @param sysOrg 实例对象
     * @return 影响行数
     */
    int update(SysOrg sysOrg);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}
