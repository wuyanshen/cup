/*
 *
 *
 *        Copyright (c) 2018-2021, wuyanshen All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *    Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *    Neither the name of the lvcoding.com developer nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *    Author: wuyanshen
 *
 *
 */

package com.lvcoding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 日志实体类
 * @author wuyanshen
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_log")
public class SysLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
     * 操作地址
     */
    @TableField(value = "addr")
    private String addr;

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


}
