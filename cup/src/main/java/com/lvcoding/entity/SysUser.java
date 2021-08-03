package com.lvcoding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户表(SysUser)实体类
 *
 * @author makejava
 * @since 2020-03-24 01:44:06
 */
@Data
public class SysUser implements Serializable {
    private static final long serialVersionUID = 448260653094233335L;
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 组织机构id
     */
    @TableField(value = "org_id")
    private Integer orgId;
    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;
    /**
     * 密码
     */
    @JsonIgnore
    @TableField(value = "password")
    private String password;
    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;
    /**
     * 电话
     */
    @TableField(value = "phone")
    private String phone;
    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;
    /**
     * 状态,1可用0不可用
     */
    @TableField(value = "status")
    private boolean status = true;
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
