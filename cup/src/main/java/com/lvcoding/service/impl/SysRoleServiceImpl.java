package com.lvcoding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvcoding.entity.SysRole;
import com.lvcoding.dao.SysRoleDao;
import com.lvcoding.entity.dto.SysRoleDTO;
import com.lvcoding.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色表(SysRole)表服务实现类
 *
 * @author makejava
 * @since 2020-03-24 01:24:31
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao,SysRole> implements SysRoleService {
    @Resource
    private SysRoleDao sysRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysRole queryById(Integer id) {
        return this.sysRoleDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SysRole> queryAllByLimit(int offset, int limit) {
        return this.sysRoleDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysRole 实例对象
     * @return 实例对象
     */
    @Override
    public SysRole insert(SysRole sysRole) {
        this.sysRoleDao.insert(sysRole);
        return sysRole;
    }

    /**
     * 修改数据
     *
     * @param sysRole 实例对象
     * @return 实例对象
     */
    @Override
    public SysRole update(SysRole sysRole) {
        this.sysRoleDao.update(sysRole);
        return this.queryById(sysRole.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.sysRoleDao.deleteById(id) > 0;
    }

    /**
     * 删除角色
     *
     * @param id
     * @return boolean
     */
    @Override
    public boolean delete(Integer id) {
        boolean flag1 = this.baseMapper.deleteById(id)>0?true:false;
        boolean flag2 = this.sysRoleDao.deleteSysRoleMenuById(id)>0?true:false;
        return flag1&&flag2;
    }

    @Override
    public List<Integer> findMenuIds(Integer id) {
        return  this.sysRoleDao.findMenuIds(id);
    }

    @Override
    public boolean saveRoleMenu(SysRoleDTO sysRoleDTO) {
        boolean flag1 = sysRoleDao.deleteRoleMenu(sysRoleDTO.getId());
        if (sysRoleDTO.getMenuIds().size()>0){
            this.sysRoleDao.saveRoleMenu(sysRoleDTO.getId(),sysRoleDTO.getMenuIds());
        }
        return flag1;
    }
}
