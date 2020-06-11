package com.lvcoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lvcoding.entity.SysRole;
import com.lvcoding.entity.dto.RoleDTO;

import java.util.List;

/**
 * 角色表(SysRole)表服务接口
 *
 * @author makejava
 * @since 2020-03-24 01:24:31
 */
public interface SysRoleService extends IService<SysRole> {

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

    boolean saveRoleMenu(RoleDTO roleDTO);
}
