package com.cup.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cup.entity.SysUser;
import com.cup.service.SysUserService;
import com.cup.util.Res;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 用户表(SysUser)表控制层
 *
 * @author makejava
 * @since 2020-03-24 01:44:06
 */
@RestController
@RequestMapping("users")
@AllArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    /**
     * 查询用户信息
     *
     * @param authentication
     * @return String
     */
    @GetMapping("info")
    public Res info(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return Res.success(user);
    }


    /**
     * 分页查询用户信息
     *
     * @param page
     * @return com.longyi.util.Res
     */
    @GetMapping("page")
    public Res page(Page page, SysUser sysUser) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(sysUser.getUsername())){
            wrapper.lambda().like(SysUser::getUsername,sysUser.getUsername());
        }
        return Res.success(this.sysUserService.page(page,wrapper));
    }

    /**
     * 新增用户
     *
     * @param sysUser
     * @return com.longyi.util.Res
     */
    @PostMapping
    public Res create(@RequestBody SysUser sysUser) {
        return Res.success(this.sysUserService.save(sysUser));
    }

    /**
     * 删除用户
     *
     * @param id
     * @return com.longyi.util.Res
     */
    @DeleteMapping("{id}")
    public Res delete(@PathVariable("id") Integer id) {
        return Res.success(this.sysUserService.deleteById(id));
    }

    /**
     * 更新用户
     *
     * @param sysUser
     * @return com.longyi.util.Res
     */
    @PutMapping
    public Res update(@RequestBody SysUser sysUser) {
        return Res.success(this.sysUserService.updateById(sysUser));
    }

}
