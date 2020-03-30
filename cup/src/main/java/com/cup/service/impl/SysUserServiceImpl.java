package com.cup.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cup.entity.SysUser;
import com.cup.dao.SysUserDao;
import com.cup.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @return IPage<SysUser>
     */
    @Override
    public IPage<SysUser> selectUserPage(Page<SysUser> page) {
        return this.sysUserDao.selectPageVo(page);
    }
}