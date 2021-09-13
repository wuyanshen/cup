package com.lvcoding.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvcoding.dao.SysDictDataMapper;
import com.lvcoding.entity.SysDictData;
import com.lvcoding.service.SysDictDataService;

import java.util.List;

@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    @Override
    public Page<SysDictData> getPage(Page<SysDictData> page, SysDictData sysDictData) {
        QueryWrapper<SysDictData> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().orderByAsc(SysDictData::getSort);

        if (StrUtil.isNotBlank(sysDictData.getLabel())) {
            queryWrapper.lambda().like(SysDictData::getLabel, sysDictData.getLabel());
        }

        if (ObjectUtil.isNotEmpty(sysDictData.getStatus())) {
            queryWrapper.lambda().eq(SysDictData::getStatus, sysDictData.getStatus());
        }

        if (StrUtil.isNotBlank(sysDictData.getTypeCode())) {
            queryWrapper.lambda().eq(SysDictData::getTypeCode, sysDictData.getTypeCode());
        }

        return this.baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean addDictData(SysDictData sysDictData) {
        int insert = this.baseMapper.insert(sysDictData);
        return insert > 0;
    }

    @Override
    public boolean updateDictData(SysDictData sysDictData) {
        int update = this.baseMapper.updateById(sysDictData);
        return update > 0;
    }

    @Override
    public boolean deleteDictData(String id) {
        int delete = this.baseMapper.deleteById(id);
        return delete > 0;
    }

    @Override
    public List<SysDictData> list(SysDictData sysDictData) {

        QueryWrapper<SysDictData> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().orderByAsc(SysDictData::getSort);

        if (StrUtil.isNotBlank(sysDictData.getLabel())) {
            queryWrapper.lambda().like(SysDictData::getLabel, sysDictData.getLabel());
        }

        if (ObjectUtil.isNotEmpty(sysDictData.getStatus())) {
            queryWrapper.lambda().eq(SysDictData::getStatus, sysDictData.getStatus());
        }

        if (StrUtil.isNotBlank(sysDictData.getTypeCode())) {
            queryWrapper.lambda().eq(SysDictData::getTypeCode, sysDictData.getTypeCode());
        }

        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public SysDictData dataDetail(String id) {
        return this.baseMapper.selectById(id);
    }
}
