package com.longyi.service.impl;

import com.longyi.entity.SysOrg;
import com.longyi.dao.SysOrgDao;
import com.longyi.service.SysOrgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 组织机构表(SysOrg)表服务实现类
 *
 * @author makejava
 * @since 2020-03-24 01:24:17
 */
@Service("sysOrgService")
public class SysOrgServiceImpl implements SysOrgService {
    @Resource
    private SysOrgDao sysOrgDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysOrg queryById(Integer id) {
        return this.sysOrgDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SysOrg> queryAllByLimit(int offset, int limit) {
        return this.sysOrgDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysOrg 实例对象
     * @return 实例对象
     */
    @Override
    public SysOrg insert(SysOrg sysOrg) {
        this.sysOrgDao.insert(sysOrg);
        return sysOrg;
    }

    /**
     * 修改数据
     *
     * @param sysOrg 实例对象
     * @return 实例对象
     */
    @Override
    public SysOrg update(SysOrg sysOrg) {
        this.sysOrgDao.update(sysOrg);
        return this.queryById(sysOrg.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.sysOrgDao.deleteById(id) > 0;
    }
}
