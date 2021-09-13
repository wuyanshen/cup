package com.lvcoding.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvcoding.entity.SysDictType;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SysDictTypeService extends IService<SysDictType>{


    Page<SysDictType> getPage(Page<SysDictType> page, SysDictType sysDictType);

    boolean addType(SysDictType sysDictType);

    boolean updateType(SysDictType sysDictType);

    boolean deleteType(String id);

    SysDictType typeDetail(String id);
}
