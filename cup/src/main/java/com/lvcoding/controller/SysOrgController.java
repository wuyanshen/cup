package com.lvcoding.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvcoding.entity.SysOrg;
import com.lvcoding.entity.dto.OrgTree;
import com.lvcoding.service.SysOrgService;
import com.lvcoding.util.Res;
import lombok.AllArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 组织机构表(SysOrg)表控制层
 *
 * @author makejava
 * @since 2020-03-24 01:24:17
 */
@RestController
@RequestMapping("orgs")
@AllArgsConstructor
public class SysOrgController {

    private final SysOrgService sysOrgService;

    /**
     * 分页查询接口
     *
     * @param page
     * @param sysOrg
     * @return com.lvcoding.util.Res
     */
    @GetMapping("page")
    public Res page(Page page, SysOrg sysOrg){
        QueryWrapper<SysOrg> queryWrapper = new QueryWrapper<>();
        if(!ObjectUtils.isEmpty(sysOrg.getId())){
            queryWrapper.lambda().eq(SysOrg::getId,sysOrg.getId());
        }
        return Res.success(sysOrgService.page(page, queryWrapper));
    }


    /**
     * 新增机构
     *
     * @param orgTree
     * @return com.lvcoding.util.Res
     */
    @PostMapping
    public Res add(@RequestBody OrgTree orgTree){
        return Res.success(sysOrgService.saveOrg(orgTree));
    }


    /**
     * 更新机构
     *
     * @param orgTree
     * @return com.lvcoding.util.Res
     */
    @PutMapping
    public Res update(@RequestBody OrgTree orgTree){
        return Res.success(sysOrgService.updateOrgById(orgTree));
    }

    /**
     * 删除机构
     *
     * @param id
     * @return com.lvcoding.util.Res
     */
    @DeleteMapping("{id}")
    public Res delete(@PathVariable("id")Integer id){
        return Res.success(sysOrgService.removeById(id));
    }


    /**
     * 获取部门树
     *
     * @param
     * @return com.lvcoding.util.Res
     */
    @GetMapping("tree")
    public Res tree(){
        return Res.success(sysOrgService.orgTree());
    }

}
