package com.cup.service.impl;

import com.cup.entity.SysMenu;
import com.cup.dao.SysMenuDao;
import com.cup.entity.dto.MenuTree;
import com.cup.service.SysMenuService;
import com.cup.util.TreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单表(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2020-03-24 01:24:05
 */
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    private SysMenuDao sysMenuDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysMenu queryById(Integer id) {
        return this.sysMenuDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SysMenu> queryAllByLimit(int offset, int limit) {
        return this.sysMenuDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    @Override
    public SysMenu insert(SysMenu sysMenu) {
        this.sysMenuDao.insert(sysMenu);
        return sysMenu;
    }

    /**
     * 修改数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    @Override
    public SysMenu update(SysMenu sysMenu) {
        this.sysMenuDao.update(sysMenu);
        return this.queryById(sysMenu.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.sysMenuDao.deleteById(id) > 0;
    }

    /**
     * 根据角色id获取用户菜单
     *
     * @param id
     * @return List<SysMenu>
     */
    @Override
    public List<MenuTree> findMenuByRoleId(Integer id) {
        List<SysMenu> menuList = sysMenuDao.findMenuByRoleId(id);
        List<MenuTree> menuTrees = menuList.stream().map(menu -> {
            MenuTree menuTree = new MenuTree();
            BeanUtils.copyProperties(menu,menuTree);
            menuTree.setId(menu.getId());
            menuTree.setParentId(menu.getMenuPid());
            return menuTree;
        }).collect(Collectors.toList());
        List<MenuTree> trees = TreeUtil.buildByRecursive(menuTrees,0);
        return trees;
    }
}
