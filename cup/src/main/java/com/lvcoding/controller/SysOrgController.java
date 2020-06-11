package com.lvcoding.controller;

import com.lvcoding.service.SysOrgService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 组织机构表(SysOrg)表控制层
 *
 * @author makejava
 * @since 2020-03-24 01:24:17
 */
@RestController
@RequestMapping("sysOrg")
@AllArgsConstructor
public class SysOrgController {

    private final SysOrgService sysOrgService;


}
