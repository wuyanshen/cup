package com.lvcoding.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvcoding.entity.SysUser;
import com.lvcoding.dao.SysUserDao;
import com.lvcoding.entity.dto.SysUserDTO;
import com.lvcoding.entity.vo.SysUserVO;
import com.lvcoding.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2020-03-24 01:44:06
 */
@Service("sysUserService")
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    private final SysUserDao sysUserDao;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysUser queryById(Integer id) {
        return this.sysUserDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public SysUser insert(SysUser sysUser) {
        this.sysUserDao.insert(sysUser);
        return sysUser;
    }

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public SysUser update(SysUser sysUser) {
        this.sysUserDao.update(sysUser);
        return this.queryById(sysUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.sysUserDao.deleteById(id) > 0;
    }

    /**
     * 查询所有用户信息
     *
     * @param sysUser
     * @return Res
     */
    @Override
    public List<SysUser> findAll(SysUser sysUser) {
        return this.sysUserDao.queryAll(sysUser);
    }

    /**
     * 分页查询用户信息
     *
     * @param page
     * @param sysUser
     * @return IPage<SysUser>
     */
    @Override
    public IPage<SysUser> selectUserPage(Page<SysUser> page, SysUser sysUser) {
        return this.sysUserDao.selectPageVo(page,sysUser);
    }

    /**
     * 修改密码
     *
     * @param sysUserVO
     * @return
     */
    @Override
    public boolean updatePwd(SysUserVO sysUserVO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserVO,sysUser);
        sysUser.setPassword(this.passwordEncoder.encode(sysUserVO.getPassword()));
        return this.sysUserDao.updatePwd(sysUser);
    }

    @Override
    public List<Integer> getRoleIdsByUserId(Integer id) {
        return this.sysUserDao.getRolesByUserId(id);
    }

    @Override
    public boolean updateUser(SysUserDTO sysUserDTO) {
        //更新用户
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserDTO,sysUser);
        if(!StringUtils.isEmpty(sysUser.getPassword())){
            sysUser.setPassword(this.passwordEncoder.encode(sysUser.getPassword()));
        }
        boolean flag1 = this.sysUserDao.updateById(sysUser)>0?true:false;

        //删除用户角色关联表
        boolean flag2 = this.sysUserDao.deleteUserRole(sysUser.getId());

        //新增用户角色关联表
        List<Boolean> collect = sysUserDTO.getRoleIds().stream().map(roleId -> {
            return this.sysUserDao.saveUserRole(sysUser.getId(), roleId);
        }).collect(Collectors.toList());

        return flag1&&flag2;
    }

    @Override
    public boolean saveUser(SysUserDTO sysUserDTO) {
        //新增用户
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserDTO,sysUser);
        sysUser.setPassword(this.passwordEncoder.encode(sysUserDTO.getPassword()));
        boolean flag1 = this.baseMapper.insert(sysUser)>0?true:false;

        //新增角色关联
        sysUserDTO.getRoleIds().stream().map(roleId -> {
            return this.sysUserDao.saveUserRole(sysUser.getId(), roleId);
        }).collect(Collectors.toList());

        return flag1;
    }
}
