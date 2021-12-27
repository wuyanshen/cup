/*
 *
 *        Copyright (c) 2021-2015, wuyanshen All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the lvcoding.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: wuyanshen
 *
 */

package com.lvcoding.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvcoding.entity.SysDictData;
import com.lvcoding.service.SysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvcoding.entity.SysDictType;
import com.lvcoding.dao.SysDictTypeMapper;
import com.lvcoding.service.SysDictTypeService;

import java.util.List;

@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    @Autowired
    private SysDictDataService sysDictDataService;

    @Override
    public Page<SysDictType> getPage(Page<SysDictType> page, SysDictType sysDictType) {
        QueryWrapper<SysDictType> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().orderByAsc(SysDictType::getSort);

        if (StrUtil.isNotBlank(sysDictType.getTypeName())) {
            queryWrapper.lambda().like(SysDictType::getTypeName, sysDictType.getTypeName());
        }

        if (ObjectUtil.isNotEmpty(sysDictType.getStatus())) {
            queryWrapper.lambda().eq(SysDictType::getStatus, sysDictType.getStatus());
        }

        if (StrUtil.isNotBlank(sysDictType.getTypeCode())) {
            queryWrapper.lambda().eq(SysDictType::getTypeCode, sysDictType.getTypeCode());
        }

        return this.baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean addType(SysDictType sysDictType) {
        int insert = this.baseMapper.insert(sysDictType);
        return insert > 0;
    }

    @Override
    public boolean updateType(SysDictType sysDictType) {
        int update = this.baseMapper.updateById(sysDictType);
        return update > 0;
    }

    @Override
    public boolean deleteType(String id) {
        int delete = this.baseMapper.deleteById(id);
        SysDictType sysDictType = this.getById(id);
        List<SysDictData> sysDictDataList = sysDictDataService.list(Wrappers.<SysDictData>lambdaQuery().eq(SysDictData::getTypeCode, sysDictType.getTypeCode()));

        if(ObjectUtil.isNotEmpty(sysDictDataList)) {
            throw new RuntimeException("该字典类型下已经有字典数据");
        }

        return delete > 0;
    }

    @Override
    public SysDictType typeDetail(String id) {
        return this.baseMapper.selectById(id);
    }
}
