package com.lvcoding.activiti;

import com.lvcoding.entity.SysUser;
import com.lvcoding.service.SysUserService;
import com.lvcoding.util.SecurityUtil;
import com.lvcoding.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wuyanshen
 * @discription 请假任务监听器
 * @date 2021-02-03 上午9:09
 */
@Slf4j
public class LeaveListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {

        if(delegateTask.getName().contains("提交请假")) {
            delegateTask.setAssignee("zhangsan");
        } else {
            delegateTask.setAssignee("admin");
        }

    }
}
