package com.lvcoding.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色数据传输类
 *
 * @author makejava
 * @since 2020-03-24 01:24:31
 */
@Data
public class SysRoleDTO implements Serializable {
    private static final long serialVersionUID = -91881003056749924L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色英文名称
     */
    private String roleCode;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态,1可用0不可用
     */
    private Integer status;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否删除,1已删0未删
     */
    private Integer delFlag;

    private List<Integer> menuIds;

}
