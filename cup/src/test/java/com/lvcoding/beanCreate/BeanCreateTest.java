package com.lvcoding.beanCreate;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;

/**
 * @author wuyanshen
 * @description 描述
 * @date 2020-07-06 2:47 下午
 */
public class BeanCreateTest {

    /**
     * bean工厂
     */
    @Test
    public void conn2Test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-beancreate.xml");
        Connection connection = (Connection) context.getBean("conn2");
        System.out.println("connection = " + connection);
    }

    /**
     * 静态工厂
     */
    @Test
    public void conn1Test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-beancreate.xml");
        Connection connection = (Connection) context.getBean("conn1");
        System.out.println("connection = " + connection);
    }

    /**
     * 实现FactoryBean
     */
    @Test
    public void connTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-beancreate.xml");
        Connection connection = (Connection) context.getBean("conn");
        System.out.println("connection = " + connection);
    }
}
