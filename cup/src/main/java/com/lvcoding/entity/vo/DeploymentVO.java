package com.lvcoding.entity.vo;

import java.util.Date;

import lombok.Data;

@Data
public class DeploymentVO {

    private String id;

    private String name;

    private Date deploymentTime;

    private String category;

    private String key;

    private String xml;

    private String tenantId;
}
