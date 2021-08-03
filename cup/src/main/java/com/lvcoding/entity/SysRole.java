package com.lvcoding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 角色表(SysRole)实体类
 *
 * @author makejava
 * @since 2020-03-24 01:24:31
 */
@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = -91881003056749924L;
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    private String roleName;
    /**
     * 角色英文名称
     */
    @TableField(value = "role_code")
    private String roleCode;
    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;
    /**
     * 状态,1可用0不可用
     */
    @TableField(value = "status")
    private Integer status;
    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    private Date createTime;
    /**
     * 是否删除,1已删0未删
     */
    @TableField(value = "del_flag")
    private Integer delFlag;

}
