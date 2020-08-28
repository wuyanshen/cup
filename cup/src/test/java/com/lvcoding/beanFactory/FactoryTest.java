package com.lvcoding.beanFactory;

import com.lvcoding.entity.SysUser;
import org.junit.Test;

/**
 * @author wuyanshen
 * @date 2020-07-05 12:28 下午
 * @discription 描述
 */
public class FactoryTest {

    @Test
    public void userTest(){
        SysUser sysUser = (SysUser) BeanFactory.getBean("user");
        sysUser.setUsername("小红");
        System.out.println(sysUser);
    }
}
