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

