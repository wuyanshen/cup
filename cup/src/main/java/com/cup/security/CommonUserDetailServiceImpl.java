package com.cup.security;

import com.cup.dao.SysMenuDao;
import com.cup.dao.SysRoleDao;
import com.cup.dao.SysUserDao;
import com.cup.entity.SysMenu;
import com.cup.entity.SysRole;
import com.cup.entity.SysUser;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuyanshen
 * @date 2020-03-24 1:44 上午
 * @discription 自定义认证查询方法
 */
@Component
public class CommonUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysMenuDao sysMenuDao;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) {
        //获取用户
        SysUser sysUser = sysUserDao.loadUserByUsername(username);

        if (sysUser == null) {
            throw new UsernameNotFoundException("您输入的用户不存在");
        }

        //获取角色
        List<SysRole> roles = sysRoleDao.loadRolesByUsername(username);
        List<String> roleCodes = roles.stream().map(sysRole -> sysRole.getRoleCode()).collect(Collectors.toList());

        //获取可以访问的菜单
        List<SysMenu> sysMenus = sysMenuDao.loadPermissionByRoleCode(roleCodes);
        List<String> urls = sysMenus.stream().map(sysMenu -> sysMenu.getUrl()).collect(Collectors.toList());

        //将可以访问的菜单和角色合并
        urls.addAll(roleCodes.stream().map(roleCode -> "ROLE_" + roleCode).collect(Collectors.toList()));
        String auths = urls.stream().collect(Collectors.joining(","));

        return new CommonUser(sysUser.getUsername(), sysUser.getPassword(), sysUser.getId(), sysUser.getOrgId(), sysUser.isStatus(), true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList(auths));
    }
}
