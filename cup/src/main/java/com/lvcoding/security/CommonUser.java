package com.lvcoding.security;

import com.lvcoding.entity.SysRole;
import com.lvcoding.entity.SysUser;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author wuyanshen
 * @date 2020-03-24 5:23 下午
 * @discription 自定义认证用户
 */
@Data
public class CommonUser implements UserDetails {

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登陆时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipAddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 用户
     */
    private SysUser sysUser;

    /**
     * 权限集合
     */
    private Set<String> permissions;

    /**
     * 角色集合
     */
    private List<SysRole> roles;

    public CommonUser(){

    }

    public CommonUser(SysUser sysUser, Set<String> permissions ,List<SysRole> roles){
        this.sysUser = sysUser;
        this.permissions = permissions;
        this.roles = roles;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
