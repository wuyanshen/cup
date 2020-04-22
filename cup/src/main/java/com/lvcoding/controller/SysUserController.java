package com.lvcoding.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvcoding.entity.SysUser;
import com.lvcoding.service.SysUserService;
import com.lvcoding.util.Res;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 查询用户信息
     *
     * @param authentication
     * @return String
     */
    @PreAuthorize("hasAuthority('sys:user:view')")
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
    @PreAuthorize("hasAuthority('sys:user:view')")
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
    @PreAuthorize("hasAuthority('sys:user:add')")
    @PostMapping
    public Res create(@RequestBody SysUser sysUser) {
        sysUser.setPassword(this.passwordEncoder.encode(sysUser.getPassword()));
        return Res.success(this.sysUserService.save(sysUser));
    }

    /**
     * 删除用户
     *
     * @param id
     * @return com.longyi.util.Res
     */
    @PreAuthorize("hasAuthority('sys:user:delete')")
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
    @PreAuthorize("hasAuthority('sys:user:update')")
    @PutMapping
    public Res update(@RequestBody SysUser sysUser) {
        sysUser.setPassword(this.passwordEncoder.encode(sysUser.getPassword()));
        return Res.success(this.sysUserService.updateById(sysUser));
    }


    /**
     * 更新密码
     *
     * @param sysUser
     * @return
     */
    @PutMapping("pwd")
    public Res updatePwd(@RequestBody SysUser sysUser){
        sysUser.setPassword(this.passwordEncoder.encode(sysUser.getPassword()));
        return Res.success(this.sysUserService.updatePwd(sysUser));
    }

    /**
     * 校验原密码是否正确
     * @param password
     * @return
     */
    @GetMapping("pwd/check")
    public Res checkPwd(@RequestParam("password")String password){
        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(passwordEncoder.matches(password,user.getPassword())){
            return Res.success("密码匹配");
        }else{
            return Res.fail("密码不匹配");
        }
    }

    /**
     * 校验用户名是否存在
     * @param username
     * @return
     */
    @GetMapping("name/check")
    public Res checkUsername(@RequestParam("username")String username){
        boolean flag = this.sysUserService.findAll(new SysUser()).stream().anyMatch(user->user.getUsername().equals(username));
        if(flag){
            return Res.fail("用户名已存在");
        }else{
            return Res.success("用户名可用");
        }
    }

}