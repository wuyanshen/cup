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

package com.lvcoding.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvcoding.entity.LeaveBill;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface LeaveBillService extends IService<LeaveBill> {

    /**
     * 新增请假
     * @param leaveBill
     */
    void addLeaveBill(LeaveBill leaveBill);

    /**
     * 分页查询
     * @param page
     * @param leaveBill
     * @return
     */
    Page findPage(Page page, LeaveBill leaveBill);

    /**
     * 删除请假
     * @param id
     * @return
     */
    boolean deleteLeaveBill(String id);

    /**
     * 修改请假
     * @param leaveBill
     * @return
     */
    boolean updateLeaveBill(LeaveBill leaveBill);
}

