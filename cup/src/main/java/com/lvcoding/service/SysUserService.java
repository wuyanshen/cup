package com.lvcoding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lvcoding.entity.SysUser;
import com.lvcoding.entity.dto.UserDTO;
import com.lvcoding.entity.vo.UserVO;

import java.util.List;

/**
 * 用户表(SysUser)表服务接口
 *
 * @author makejava
 * @since 2020-03-24 01:44:06
 */
public interface SysUserService extends IService<SysUser> {


    /**
     * 更新密码
     *
     * @param sysUser
     * @return boolean
     */
    boolean updatePwd(UserVO sysUser);

    /**
     * 通过用户id查询角色id集
     *
     * @param id
     * @return java.lang.String
     */
    List<Integer> getRoleIdsByUserId(Integer id);

    /**
     * 更新用户
     *
     * @param userDTO
     * @return boolean
     */
    boolean updateUser(UserDTO userDTO);

    /**
     * 保存用户
     *
     * @param userDTO
     * @return boolean
     */
    boolean saveUser(UserDTO userDTO);
}
