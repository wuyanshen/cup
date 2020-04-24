package com.lvcoding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName(value = "sys_log")
public class SysLog {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 日志类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 日志标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 操作ip地址
     */
    @TableField(value = "ip")
    private String ip;

    /**
     * 请求uri
     */
    @TableField(value = "request_uri")
    private String requestUri;

    /**
     * 请求方式(POST,GET,PUT,DELETE)
     */
    @TableField(value = "method")
    private String method;

    /**
     * 请求提交参数
     */
    @TableField(value = "params")
    private String params;

    /**
     * 响应数据
     */
    @TableField(value = "response")
    private String response;

    /**
     * 请求时间
     */
    @TableField(value = "time")
    private Long time;

    /**
     * 操作人
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 是否删除,1已删0未删
     */
    @TableField(value = "del_flag")
    private Integer delFlag;

}
