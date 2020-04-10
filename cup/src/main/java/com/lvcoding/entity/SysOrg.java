package com.lvcoding.entity;

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
public class SysOrg implements Serializable {
    private static final long serialVersionUID = 772244235379764292L;
    /**
    * 主键id
    */
    private Integer id;
    /**
    * 父节点id
    */
    private Integer orgPid;
    /**
    * 所有的父节点
    */
    private String orgPids;
    /**
    * 是否叶子节点
    */
    private Integer isLeaf;
    /**
    * 组织机构名称
    */
    private String orgName;
    /**
    * 地址
    */
    private String address;
    /**
    * 状态,1启用0无效
    */
    private Integer status;
    /**
    * 排序
    */
    private Integer sort;
    /**
    * 菜单的层级
    */
    private Integer level;
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
