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

package com.lvcoding.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvcoding.entity.LeaveBill;
import com.lvcoding.service.LeaveBillService;
import com.lvcoding.util.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 请假接口
 */
@RequestMapping("leave")
@RestController
public class LeaveBillController {

    @Autowired
    private LeaveBillService leaveBillService;

    /**
     * 提交请假
     * @param leaveBill
     * @return
     */
    @PostMapping
    public Res add(@RequestBody LeaveBill leaveBill) {
        leaveBillService.addLeaveBill(leaveBill);
        return Res.success("提交请假成功");
    }


    /**
     * 分页查询
     * @param page
     * @param leaveBill
     * @return
     */
    @GetMapping("page")
    public Res page(Page page, LeaveBill leaveBill){
        return Res.success(leaveBillService.findPage(page, leaveBill));
    }

    /**
     * 删除请假
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Res delete(@PathVariable("id") String id) {
        return Res.success(leaveBillService.deleteLeaveBill(id));
    }

    /**
     * 修改请假
     * @param leaveBill
     * @return
     */
    @PutMapping("update")
    public Res update(@RequestBody LeaveBill leaveBill) {
        return Res.success(leaveBillService.updateLeaveBill(leaveBill));
    }
}
