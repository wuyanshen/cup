package com.lvcoding;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvcoding.dao.SysLogDao;
import com.lvcoding.entity.SysLog;
import com.lvcoding.service.SysLogService;
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLog> implements SysLogService{

}