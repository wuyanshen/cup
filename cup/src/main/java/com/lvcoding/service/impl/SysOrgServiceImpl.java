/*
 *
 *
 *        Copyright (c) 2018-2021, wuyanshen All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *    Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *    Neither the name of the lvcoding.com developer nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *    Author: wuyanshen
 *
 *
 */

package com.lvcoding.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvcoding.entity.SysOrg;
import com.lvcoding.dao.SysOrgMapper;
import com.lvcoding.entity.dto.OrgTree;
import com.lvcoding.service.SysOrgService;
import com.lvcoding.util.TreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 组织机构表(SysOrg)表服务实现类
 *
 * @author makejava
 * @since 2020-03-24 01:24:17
 */
@Service
public class SysOrgServiceImpl extends ServiceImpl<SysOrgMapper, SysOrg> implements SysOrgService {

    @Override
    public List<OrgTree> orgTree(SysOrg sysOrg) {
        QueryWrapper<SysOrg> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotBlank(sysOrg.getOrgName())) {
            queryWrapper.lambda().like(SysOrg::getOrgName, sysOrg.getOrgName());
        }

        List<SysOrg> sysOrgs = baseMapper.selectList(queryWrapper);
        List<OrgTree> collect = sysOrgs.stream().map(org -> {
            OrgTree orgTree = new OrgTree();
            BeanUtils.copyProperties(org, orgTree);
            orgTree.setId(org.getId());
            orgTree.setParentId(org.getPid());
            return orgTree;
        }).collect(Collectors.toList());
        List<OrgTree> orgTrees = TreeUtil.buildByRecursive(collect, 0);
        return orgTrees;
    }

    @Transactional
    @Override
    public Boolean saveOrg(OrgTree orgTree) {
        SysOrg sysOrg = new SysOrg();
        BeanUtils.copyProperties(orgTree, sysOrg);
        sysOrg.setPid(orgTree.getParentId());
        //设置父节点集合和level
        this.setOrgPidsAndLevel(sysOrg);
        //新增节点默认没有子节点，因此是叶子节点
        sysOrg.setIsLeaf(1);
        //新增节点默认有效
        sysOrg.setStatus(1);
        //设置菜单级别
        if (sysOrg.getPid() == 0) {
            sysOrg.setLevel(1);
        }
        //更新父节点为非叶子节点
        SysOrg parent = new SysOrg();
        parent.setId(sysOrg.getPid());
        parent.setIsLeaf(0);
        this.baseMapper.updateById(parent);

        return this.baseMapper.insert(sysOrg) > 0;
    }

    @Override
    public Boolean updateOrgById(OrgTree orgTree) {
        SysOrg sysOrg = new SysOrg();
        BeanUtils.copyProperties(orgTree, sysOrg);
        sysOrg.setPid(orgTree.getParentId());
        //设置父节点集合和level
        this.setOrgPidsAndLevel(sysOrg);
        return this.baseMapper.updateById(sysOrg) > 0;
    }

    @Override
    public List<SysOrg> getOrgList(SysOrg sysOrg) {

        QueryWrapper<SysOrg> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotBlank(sysOrg.getOrgName())) {
            queryWrapper.lambda().like(SysOrg::getOrgName, sysOrg.getOrgName());
        }
        List<SysOrg> sysOrgList = baseMapper.selectList(queryWrapper);

        return sysOrgList;
    }

    private void setOrgPidsAndLevel(SysOrg sysOrg) {
        QueryWrapper<SysOrg> queryWrapper = new QueryWrapper<>();
        List<SysOrg> orgList = baseMapper.selectList(queryWrapper);
        for (SysOrg org : orgList) {
            if (org.getId().equals(sysOrg.getPid())) {
                if (ObjectUtils.isEmpty(org.getPids()) || org.getPids().equals("0")) {
                    sysOrg.setPids(org.getId() + "");
                } else {
                    sysOrg.setPids(org.getPids() + "," + sysOrg.getPid());
                }

                sysOrg.setLevel(org.getLevel() + 1);
            }
        }
    }
}
