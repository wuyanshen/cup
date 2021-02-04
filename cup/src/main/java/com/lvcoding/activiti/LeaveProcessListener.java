package com.lvcoding.activiti;

import com.lvcoding.entity.SysUser;
import com.lvcoding.service.SysUserService;
import com.lvcoding.util.SecurityUtil;
import com.lvcoding.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wuyanshen
 * @discription 请假任务监听器
 * @date 2021-02-03 上午9:09
 */
@Slf4j
public class LeaveProcessListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        // 当前登录用户
        String username = Objects.requireNonNull(SecurityUtil.getUser()).getUsername();

        SysUserService sysUserService = SpringContextHolder.getBean("sysUserService");
        // 获取用户上级
        List<SysUser> userList = sysUserService.findSuperior(username);
        if (userList == null) {
            log.info("用户 {} 没有上级，将由当前用户作为任务候选人", username);
            delegateTask.addCandidateUser(username);
        } else {
            List<String> users = userList.stream().map(SysUser::getUsername).collect(Collectors.toList());
            log.info("用户 {} 有上级，任务候选人：", String.join(",", users));
            delegateTask.addCandidateUsers(users);
        }
    }
}
