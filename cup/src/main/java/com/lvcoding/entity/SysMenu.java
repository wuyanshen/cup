package com.lvcoding.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 菜单表(SysMenu)实体类
 *
 * @author makejava
 * @since 2020-03-24 01:24:05
 */
@Data
public class SysMenu implements Serializable {
    private static final long serialVersionUID = -55455731078448652L;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 父id
     */
    private Integer menuPid;
    /**
     * 所有的父id
     */
    private String menuPids;
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
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除,1已删0未删
     */
    private Integer delFlag;


}
