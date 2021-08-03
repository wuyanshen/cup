package com.lvcoding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 组织机构表(SysOrg)实体类
 *
 * @author makejava
 * @since 2020-03-24 01:24:17
 */
@Data
@TableName(value = "sys_org")
public class SysOrg implements Serializable {
    private static final long serialVersionUID = 772244235379764292L;
    /**
    * 主键id
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
    * 父节点id
    */
    @TableField(value = "org_pid")
    private Integer orgPid;
    /**
    * 所有的父节点
    */
    @TableField(value = "org_pids")
    private String orgPids;
    /**
    * 是否叶子节点
    */
    @TableField(value = "is_leaf")
    private Integer isLeaf;
    /**
    * 组织机构名称
    */
    @TableField(value = "org_name")
    private String orgName;
    /**
    * 地址
    */
    @TableField(value = "address")
    private String address;
    /**
    * 状态,1启用0无效
    */
    @TableField(value = "status")
    private Integer status;
    /**
    * 排序
    */
    @TableField(value = "sort")
    private Integer sort;
    /**
    * 菜单的层级
    */
    @TableField(value = "level")
    private Integer level;
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
