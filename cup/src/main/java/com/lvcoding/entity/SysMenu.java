package com.lvcoding.entity;

import lombok.Data;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 菜单表(SysMenu)实体类
 *
 * @author makejava
 * @since 2020-03-24 01:24:05
 */
@Data
@TableName(value = "sys_menu")
public class SysMenu implements Serializable {
    private static final long serialVersionUID = -55455731078448652L;
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 父id
     */
    @TableField(value = "menu_pid")
    private Integer menuPid;
    /**
     * 所有的父id
     */
    @TableField(value = "menu_pids")
    private String menuPids;
    /**
     * 菜单名称
     */
    @TableField(value = "menu_name")
    private String menuName;
    /**
     * 菜单路径
     */
    @TableField(value = "url")
    private String url;
    /**
     * 权限标识
     */
    @TableField(value = "permission")
    private String permission;

    /**
     * 菜单类型(0菜单1按钮)
     */
    @TableField(value = "type")
    private String type;

    /**
     * VUE页面
     */
    @TableField(value = "component")
    private String component;

    /**
     * 路由缓冲(1开启0关闭)
     */
    @TableField(value = "keep_alive")
    private String keepAlive;

    /**
     * 菜单图标
     */
    @TableField(value = "icon")
    private String icon;
    /**
     * 状态,1可用0不可用
     */
    @TableField(value = "status")
    private Integer status;
    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
    /**
     * 是否删除,1已删0未删
     */
    @TableField(value = "del_flag")
    private Integer delFlag;


}
