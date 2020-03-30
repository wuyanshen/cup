package com.cup.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author wuyanshen
 * @date 2020-03-29 4:09 下午
 * @discription 菜单树页面传值VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends TreeNode {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单路径
     */
    private String url;
    /**
     * 权限标识
     */
    private String permission;

    /**
     * 菜单类型(0菜单1按钮)
     */
    private String type;

    /**
     * VUE页面
     */
    private String component;

    /**
     * 路由缓冲(1开启0关闭)
     */
    private String keepAlive;

    /**
     * 是否叶子节点，1:是，0:不是
     */
    private Integer isLeaf;
    /**
     * 菜单第几级
     */
    private Integer level;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 状态,1可用0不可用
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;

}
