package com.lvcoding.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lvcoding.entity.SysOrg;
import com.lvcoding.entity.dto.OrgTree;

import java.util.List;

/**
 * 组织机构表(SysOrg)表服务接口
 *
 * @author makejava
 * @since 2020-03-24 01:24:17
 */
public interface SysOrgService extends IService<SysOrg> {

    List<OrgTree> orgTree();

    Boolean saveOrg(SysOrg sysOrg);
}
