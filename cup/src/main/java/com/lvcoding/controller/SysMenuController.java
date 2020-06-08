package com.lvcoding.controller;

import com.lvcoding.entity.SysMenu;
import com.lvcoding.entity.dto.MenuTree;
import com.lvcoding.log.SysLog;
import com.lvcoding.security.CommonUser;
import com.lvcoding.service.SysMenuService;
import com.lvcoding.util.Res;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单表(SysMenu)表控制层
 *
 * @author makejava
 * @since 2020-03-24 01:24:05
 */
@RestController
@RequestMapping("menus")
public class SysMenuController {
    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysMenu selectOne(Integer id) {
        return this.sysMenuService.queryById(id);
    }


    /**
     * 获取当前用户的菜单树
     *
     * @param authentication
     * @return Res
     */
    @GetMapping("tree")
    public Res menuTree(Authentication authentication) {
        CommonUser commonUser = (CommonUser) authentication.getPrincipal();
        List<Integer> roleIds = commonUser.getSysRoles().stream().map(sysRole -> sysRole.getId()).collect(Collectors.toList());
        List<MenuTree> list = sysMenuService.findMenuByRoleIds(roleIds);
        return Res.success(list);
    }

    /**
     * 查询菜单
     *
     * @param authentication
     * @return Res
     */
    @SysLog(type = "2",value = "查询菜单")
    @GetMapping("treePage")
    public Res menuTreePage(Authentication authentication) {
        CommonUser commonUser = (CommonUser) authentication.getPrincipal();
        List<Integer> roleIds = commonUser.getSysRoles().stream().map(sysRole -> sysRole.getId()).collect(Collectors.toList());
        List<MenuTree> list = sysMenuService.findMenuByRoleIds(roleIds);
        return Res.success(list);
    }

    /**
     * 新增菜单
     *
     * @param
     * @return MenuTree
     */
    @SysLog(type = "2",value = "新增菜单")
    @PostMapping
    public Res add(@RequestBody MenuTree menuTree){
        return Res.success(sysMenuService.addMenu(menuTree));
    }

    /**
     * 修改菜单
     *
     * @param sysMenu
     * @return Res
     */
    @SysLog(type = "2",value = "修改菜单")
    @PutMapping
    public Res update(@RequestBody SysMenu sysMenu){
        return Res.success(sysMenuService.update(sysMenu));
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return com.lvcoding.util.Res
     */
    @SysLog(type = "2",value = "删除菜单")
    @DeleteMapping("{id}")
    public Res delete(@PathVariable("id")Integer id){
        return Res.success(sysMenuService.deleteById(id));
    }
}
