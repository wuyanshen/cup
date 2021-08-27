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

    List<OrgTree> orgTree(SysOrg sysOrg);

    Boolean saveOrg(OrgTree orgTree);

    Boolean updateOrgById(OrgTree orgTree);

    List<SysOrg> getOrgList(SysOrg sysOrg);
}
