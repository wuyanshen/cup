package com.lvcoding.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvcoding.entity.SysDictData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysDictDataService extends IService<SysDictData>{


    Page<SysDictData> getPage(Page<SysDictData> page, SysDictData sysDictData);

    boolean addDictData(SysDictData sysDictData);

    boolean updateDictData(SysDictData sysDictData);

    boolean deleteDictData(String id);

    List<SysDictData> list(SysDictData sysDictData);

    SysDictData dataDetail(String id);
}
