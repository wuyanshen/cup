package com.lvcoding.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 机构树实体类
 */
@Data
public class OrgTree extends TreeNode implements Serializable {
    private static final long serialVersionUID = 772244235379764292L;

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

}
