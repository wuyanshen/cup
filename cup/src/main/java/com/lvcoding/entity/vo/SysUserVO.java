package com.lvcoding.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户(SysUserVO)视图类
 *
 * @author makejava
 * @since 2020-03-24 01:44:06
 */
@Data
public class SysUserVO implements Serializable {
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
}
