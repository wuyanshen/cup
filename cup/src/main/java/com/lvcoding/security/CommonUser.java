package com.lvcoding.security;

import com.lvcoding.entity.SysRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * @author wuyanshen
 * @date 2020-03-24 5:23 下午
 * @discription 自定义认证用户
 */
public class CommonUser extends User {

    /**
     * 机构id
     */
    @Getter
    private Integer orgId;

    /**
     * 角色集合
     */
    @Getter
    private List<SysRole> sysRoles;

    public CommonUser(String username, String password, Integer orgId, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, List<SysRole> sysRoles, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.orgId = orgId;
        this.sysRoles = sysRoles;
    }
}
