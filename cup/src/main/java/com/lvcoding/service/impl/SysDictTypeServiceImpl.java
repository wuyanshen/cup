package com.lvcoding.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvcoding.entity.SysDictType;
import com.lvcoding.dao.SysDictTypeMapper;
import com.lvcoding.service.SysDictTypeService;

@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

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
        return delete > 0;
    }

    @Override
    public SysDictType typeDetail(String id) {
        return this.baseMapper.selectById(id);
    }
}
