package com.lvcoding.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户数据传输
 */
@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 448260653094233335L;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 组织机构id
     */
    private Integer orgId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 状态,1可用0不可用
     */
    private boolean status = true;
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

    /**
     * 用户角色id集合
     */
    private List<Integer> roleIds;
}
