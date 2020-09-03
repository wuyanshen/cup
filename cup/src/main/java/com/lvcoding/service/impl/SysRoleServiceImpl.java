package com.lvcoding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvcoding.entity.SysRole;
import com.lvcoding.dao.SysRoleMapper;
import com.lvcoding.entity.dto.RoleDTO;
import com.lvcoding.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色表(SysRole)表服务实现类
 *
 * @author makejava
 * @since 2020-03-24 01:24:31
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements SysRoleService {

    /**
     * 删除角色
     *
     * @param id
     * @return boolean
     */
    @Override
    public boolean delete(Integer id) {
        boolean flag1 = this.baseMapper.deleteById(id)>0?true:false;
        boolean flag2 = this.baseMapper.deleteSysRoleMenuById(id)>0?true:false;
        return flag1&&flag2;
    }

    @Override
    public List<Integer> findMenuIds(Integer id) {
        return  this.baseMapper.findMenuIds(id);
    }

    @Override
    public boolean saveRoleMenu(RoleDTO roleDTO) {
        boolean flag1 = baseMapper.deleteRoleMenu(roleDTO.getId());
        if (roleDTO.getMenuIds().size()>0){
            this.baseMapper.saveRoleMenu(roleDTO.getId(), roleDTO.getMenuIds());
        }
        return flag1;
    }
}
