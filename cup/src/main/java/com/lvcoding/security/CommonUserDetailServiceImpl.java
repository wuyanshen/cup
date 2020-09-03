package com.lvcoding.security;

import com.lvcoding.dao.SysMenuMapper;
import com.lvcoding.dao.SysRoleMapper;
import com.lvcoding.dao.SysUserMapper;
import com.lvcoding.entity.SysMenu;
import com.lvcoding.entity.SysRole;
import com.lvcoding.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wuyanshen
 * @date 2020-03-24 1:44 上午
 * @discription 自定义认证查询方法
 */
@Component
@AllArgsConstructor
public class CommonUserDetailServiceImpl implements UserDetailsService {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysMenuMapper sysMenuMapper;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) {
        //获取用户
        SysUser sysUser = sysUserMapper.loadUserByUsername(username);

        if (sysUser == null) {
            throw new UsernameNotFoundException("您输入的用户不存在");
        }

        //获取角色
        List<SysRole> roles = sysRoleMapper.loadRolesByUsername(username);
        List<String> roleCodes = roles.stream().map(sysRole -> sysRole.getRoleCode()).collect(Collectors.toList());

        //获取可以访问的菜单
        List<SysMenu> sysMenus = sysMenuMapper.loadPermissionByRoleCode(roleCodes);
        List<String> urls = sysMenus.stream().map(sysMenu -> sysMenu.getUrl()).filter(url -> !StringUtils.isEmpty(url)).collect(Collectors.toList());
        //获取可以访问的按钮
        List<String> permissions = sysMenus.stream().map(sysMenu -> sysMenu.getPermission()).filter(permission-> !StringUtils.isEmpty(permission)).collect(Collectors.toList());

        //将按钮权限合并
        urls.addAll(permissions);

        //将可以访问的菜单和角色合并
        urls.addAll(roleCodes.stream().map(roleCode -> "ROLE_" + roleCode).collect(Collectors.toList()));
        String auths = urls.stream().collect(Collectors.joining(","));
        Set<String> authorities = Arrays.stream(auths.split(",")).collect(Collectors.toSet());

//        return new CommonUser(sysUser.getUsername(), sysUser.getPassword(), sysUser.getOrgId(), sysUser.getId(), sysUser.isStatus(), true, true, true,roles, AuthorityUtils.commaSeparatedStringToAuthorityList(auths));
        return new CommonUser(sysUser, authorities, roles);
    }
}
