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

package com.lvcoding.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvcoding.entity.SysDictData;
import com.lvcoding.entity.SysDictType;
import com.lvcoding.service.SysDictDataService;
import com.lvcoding.service.SysDictTypeService;
import com.lvcoding.util.Res;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @description 字典接口
 * @date   2021-09-08 下午6:28
 * @author  wuyanshen
 */
@RequestMapping("dicts")
@RestController
@AllArgsConstructor
public class SysDictController {

    private final SysDictTypeService sysDictTypeService;
    private final SysDictDataService sysDictDataService;


    /**
     * 查询字典类型详情
     *
     * @param id
     * @return Res
     */
    @GetMapping("type/{id}")
    public Res typeDetail(@PathVariable("id")String id) {
        return Res.success(sysDictTypeService.typeDetail(id));
    }

    /**
     * 查询字典类型分页
     *
     * @param page
     * @param sysDictType
     * @return Res
     */
    @GetMapping("type/page")
    public Res typePage(Page<SysDictType> page, SysDictType sysDictType) {
        return Res.success(sysDictTypeService.getPage(page, sysDictType));
    }

    /**
     * 新增字典类型
     *
     * @param sysDictType
     * @return Res
     */
    @PostMapping("type/add")
    public Res addType(@RequestBody SysDictType sysDictType) {
        return Res.success(sysDictTypeService.addType(sysDictType));
    }

    /**
     * 更新字典类型
     *
     * @param sysDictType
     * @return Res
     */
    @PutMapping("type/update")
    public Res updateType(@RequestBody SysDictType sysDictType) {
        return Res.success(sysDictTypeService.updateType(sysDictType));
    }

    /**
     * 删除字典类型
     *
     * @param id
     * @return Res
     */
    @DeleteMapping("type/{id}")
    public Res deleteType(@PathVariable String id) {
        return Res.success(sysDictTypeService.deleteType(id));
    }

    /**
     * 查询字典数据分页
     *
     * @param page
     * @param sysDictType
     * @return Res
     */
    @GetMapping("data/page")
    public Res dataPage(Page<SysDictData> page, SysDictData sysDictData) {
        return Res.success(sysDictDataService.getPage(page, sysDictData));
    }

    /**
     * 查询字典数据list
     *
     * @param sysDictData
     * @return Res
     */
    @GetMapping("data/list")
    public Res dataList(SysDictData sysDictData) {
        return Res.success(sysDictDataService.list(sysDictData));
    }

    /**
     * 新增字典数据
     *
     * @param sysDictData
     * @return Res
     */
    @PostMapping("data/add")
    public Res addData(@RequestBody SysDictData sysDictData) {
        return Res.success(sysDictDataService.addDictData(sysDictData));
    }

    /**
     * 更新字典数据
     *
     * @param sysDictData
     * @return Res
     */
    @PutMapping("data/update")
    public Res updateData(@RequestBody SysDictData sysDictData) {
        return Res.success(sysDictDataService.updateDictData(sysDictData));
    }

    /**
     * 删除字典数据
     *
     * @param id
     * @return Res
     */
    @DeleteMapping("data/{id}")
    public Res deleteData(@PathVariable String id) {
        return Res.success(sysDictDataService.deleteDictData(id));
    }

    /**
     * 查询字典数据详情
     *
     * @param id
     * @return Res
     */
    @GetMapping("data/{id}")
    public Res dataDetail(@PathVariable("id")String id) {
        return Res.success(sysDictDataService.dataDetail(id));
    }
}
