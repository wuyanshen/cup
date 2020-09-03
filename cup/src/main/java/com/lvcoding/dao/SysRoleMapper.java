package com.lvcoding.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lvcoding.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色表(SysRole)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-24 01:24:31
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户名获取角色
     *
     * @param username
     * @return List<SysRole>
     */
    List<SysRole> loadRolesByUsername(String username);

    int deleteSysRoleMenuById(Integer id);

    List<Integer> findMenuIds(@Param("roleId") Integer id);

    boolean saveRoleMenu(@Param("roleId")Integer roleId,@Param("menuIds") List<Integer> menuIds);

    boolean deleteRoleMenu(@Param("roleId")Integer id);
}
