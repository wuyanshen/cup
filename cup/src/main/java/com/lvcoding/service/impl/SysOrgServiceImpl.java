package com.lvcoding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvcoding.entity.SysOrg;
import com.lvcoding.dao.SysOrgDao;
import com.lvcoding.entity.dto.OrgTree;
import com.lvcoding.service.SysOrgService;
import com.lvcoding.util.TreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 组织机构表(SysOrg)表服务实现类
 *
 * @author makejava
 * @since 2020-03-24 01:24:17
 */
@Service
public class SysOrgServiceImpl extends ServiceImpl<SysOrgDao, SysOrg> implements SysOrgService {

    @Override
    public List<OrgTree> orgTree() {
        QueryWrapper<SysOrg> queryWrapper = new QueryWrapper<>();
        List<SysOrg> sysOrgs = baseMapper.selectList(queryWrapper);
        List<OrgTree> collect = sysOrgs.stream().map(sysOrg -> {
            OrgTree orgTree = new OrgTree();
            BeanUtils.copyProperties(sysOrg, orgTree);
            orgTree.setId(sysOrg.getId());
            orgTree.setParentId(sysOrg.getOrgPid());
            return orgTree;
        }).collect(Collectors.toList());
        List<OrgTree> orgTrees = TreeUtil.buildByRecursive(collect, 0);
        return orgTrees;
    }

    @Transactional
    @Override
    public Boolean saveOrg(SysOrg sysOrg) {
        //设置父节点集合和level
        this.setOrgPidsAndLevel(sysOrg);
        //新增节点默认没有子节点，因此是叶子节点
        sysOrg.setIsLeaf(1);
        //新增节点默认有效
        sysOrg.setStatus(1);
        //更新父节点为非叶子节点
        SysOrg parent = new SysOrg();
        parent.setId(sysOrg.getOrgPid());
        parent.setIsLeaf(0);
        this.baseMapper.updateById(parent);

        return this.baseMapper.insert(sysOrg)>0;
    }

    private void setOrgPidsAndLevel(SysOrg sysOrg){
        QueryWrapper<SysOrg> queryWrapper = new QueryWrapper<>();
        List<SysOrg> orgList = baseMapper.selectList(queryWrapper);
        for (SysOrg org:orgList){
            if(org.getId().equals(sysOrg.getOrgPid())){
                sysOrg.setOrgPids(org.getOrgPids()+","+sysOrg.getOrgPid());
                sysOrg.setLevel(org.getLevel()+1);
            }
        }
    }
}
