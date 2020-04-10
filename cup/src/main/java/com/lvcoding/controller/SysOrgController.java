package com.lvcoding.controller;

import com.lvcoding.entity.SysOrg;
import com.lvcoding.service.SysOrgService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 组织机构表(SysOrg)表控制层
 *
 * @author makejava
 * @since 2020-03-24 01:24:17
 */
@RestController
@RequestMapping("sysOrg")
public class SysOrgController {
    /**
     * 服务对象
     */
    @Resource
    private SysOrgService sysOrgService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysOrg selectOne(Integer id) {
        return this.sysOrgService.queryById(id);
    }

}
