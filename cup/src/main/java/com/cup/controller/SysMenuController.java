package com.cup.controller;

import com.cup.entity.SysMenu;
import com.cup.entity.dto.MenuTree;
import com.cup.service.SysMenuService;
import com.cup.util.Res;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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


    @GetMapping("tree")
    public Res menuTree(){
        List<MenuTree> list = sysMenuService.findMenuByRoleId(1);
        return Res.success(list);
    }
}
