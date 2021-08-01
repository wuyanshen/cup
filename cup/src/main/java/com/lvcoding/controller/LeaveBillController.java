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
