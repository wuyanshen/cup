package com.lvcoding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_dict_type")
public class SysDictType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 字典类型编码
     */
    @TableField(value = "type_code")
    private String typeCode;

    /**
     * 字典类型名称
     */
    @TableField(value = "type_name")
    private String typeName;

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
     * 租户id
     */
    @TableField(value = "tenant_id")
    private Integer tenantId;
}
